package net.sjl.k8s;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.Job;
import io.fabric8.kubernetes.api.model.JobStatus;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerBuilder;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.api.model.ResourceQuotaBuilder;
import io.fabric8.kubernetes.api.model.Status;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import io.fabric8.kubernetes.client.APIGroupNotAvailableException;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.fabric8.kubernetes.client.Watcher.Action.ERROR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * https://github.com/fabric8io/kubernetes-client
 * 
 * @author mobile
 *
 */

public class FabricExample {
	private static final Logger logger = LoggerFactory.getLogger(FabricExample.class);

	public static void main(String[] args) {
		/*
		 * String master = "https://localhost:8443/"; if (args.length == 1) {
		 * master = args[0]; } Config config = new
		 * ConfigBuilder().withMasterUrl(master).build(); final KubernetesClient
		 * client = new DefaultKubernetesClient(config);
		 */

		try (final KubernetesClient client = new DefaultKubernetesClient()) {
			try (Watch watch = client.replicationControllers().inNamespace("thisisatest").withResourceVersion("0")
					.watch(new Watcher<ReplicationController>() {
						@Override
						public void eventReceived(Action action, ReplicationController resource) {
							logger.info("eventReceived - {}: {}", action, resource);
						}

						@Override
						public void onClose(KubernetesClientException e) {
							if (e != null) {
								e.printStackTrace();
								logger.error(e.getMessage(), e);
							}
						}
					})) {

				client.namespaces().withName("thisisatest").delete();
				log("Deleted namespace firstly");

				// Create a namespace for all our stuff
				Namespace ns = new NamespaceBuilder().withNewMetadata().withName("thisisatest")
						.addToLabels("this", "rocks").endMetadata().build();
				log("Created namespace", client.namespaces().create(ns));

				// Get the namespace by name
				log("Get namespace by name", client.namespaces().withName("thisisatest").get());
				// Get the namespace by label
				log("Get namespace by label", client.namespaces().withLabel("this", "rocks").list());

				ResourceQuota quota = new ResourceQuotaBuilder().withNewMetadata().withName("pod-quota").endMetadata()
						.withNewSpec().addToHard("pods", new Quantity("10")).endSpec().build();
				log("Create resource quota", client.resourceQuotas().inNamespace("thisisatest").create(quota));

				try {
					log("Get jobs in default namespace", client.extensions().jobs().inNamespace("default").list());
					log("Get jobs in thisisatest namespace",
							client.extensions().jobs().inNamespace("thisisatest").list());
				} catch (APIGroupNotAvailableException e) {
					log("Skipping jobs example - extensions API group not available");
				}

				Job piJob = client.extensions().jobs().inNamespace("thisisatest").createNew().withNewMetadata()
						.withName("pi-job").addToLabels("creator", "allen").endMetadata().withNewSpec()
						.withNewTemplate().withNewMetadata().withName("pi").endMetadata().withNewSpec()
						.withContainers(new ContainerBuilder().withName("pi").withImage("perl")
								.withCommand(Arrays.asList("perl", "-Mbignum=bpi", "-wle", "print bpi(2000)")).build())
						.withRestartPolicy("Never").endSpec().endTemplate().endSpec().done();

				final CountDownLatch closeLatch = new CountDownLatch(1);
				client.extensions().jobs().inNamespace("thisisatest").withName("pi-job").watch(new Watcher<Job>() {
					@Override
					public void eventReceived(Action action, Job job) {
						logger.info(
								"Job eventReceived - action: {}, version: {}, startTime: {}, completionTime: {}, suceeded: {}",
								action, job.getMetadata().getResourceVersion(), job.getStatus().getStartTime(),
								job.getStatus().getCompletionTime(), job.getStatus().getSucceeded());

						Integer status = job.getStatus().getSucceeded();
						if (status != null && status.intValue() == 1) {
							logger.info("Job runs to completion with successful - action: {}, suceeded: {}", action,
									status);
							closeLatch.countDown();
						}
					}

					@Override
					public void onClose(KubernetesClientException e) {
						logger.debug("Watcher onClose");
						if (e != null) {
							logger.error(e.getMessage(), e);
							closeLatch.countDown();
						}
					}
				});

				// closeLatch.await(100, TimeUnit.SECONDS);
				closeLatch.await();

			} finally {
				// And finally clean up the namespace
				// client.namespaces().withName("thisisatest").delete();
				log("Deleted namespace");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			Throwable[] suppressed = e.getSuppressed();
			if (suppressed != null) {
				for (Throwable t : suppressed) {
					logger.error(t.getMessage(), t);
				}
			}
		}
	}

//	private void deploy() {
//		
//		final KubernetesClient client = new DefaultKubernetesClient();
//		 String targetNS = "";
//		 logger.info("Launch job with target namespace " + targetNS);
//		
//		 PodBuilder podBuilder = new PodBuilder();
//		 Deployment.Jobs.Orderedlist.Job jobConfig = job.getJobConfig();
//		 if (jobConfig != null) {
//		
//		 podBuilder =
//		 podBuilder.withNewMetadata().withName(jobConfig.getPodname())
//		 .addToLabels("purpose", "ecm-cfg-container-demo").endMetadata();
//		
//		 PodSpecBuilder podSpecBuilder = new PodSpecBuilder();
//		
//		 ContainerBuilder containerBuilder = new
//		 ContainerBuilder().withName(jobConfig.getPodname())
//		 .withImage(jobConfig.getImageurl());
//		 Map<String, String> envVars = job.getEnvironmentVars();
//		 if (envVars != null && !envVars.isEmpty()) {
//		 List envs = new ArrayList<>();
//		 EnvVarBuilder envVarBuilder = new EnvVarBuilder();
//		 for (String envName : envVars.keySet()) {
//		 envs.add(envVarBuilder.withName(envName).withValue(envVars.get(envName)).build());
//		 }
//		 containerBuilder = containerBuilder.withEnv(envs);
//		 }
//		
//
//		 String cmd = "bash";
//		 if (cmd != null && cmd.length() > 0) {
//		 containerBuilder = containerBuilder.withCommand(cmd);
//		 }
//		 
//		 List<String> cmdArgs = args.getArg();
//		 containerBuilder = containerBuilder.withArgs(cmdArgs);
//		
//		
//		 Deployment.Jobs.Orderedlist.Job.Mounts mounts =
//		 jobConfig.getMounts();
//		 if (mounts != null && mounts.getMount() != null &&
//		 !mounts.getMount().isEmpty()) {
//		 List volMounts = new ArrayList();
//		 List vms = new ArrayList<>();
//		 for (Object mount :
//		 volMounts) {
//		 VolumeMountBuilder vmountBuilder = new
//		 VolumeMountBuilder().withName(mount.getVolume())
//		 .withMountPath(mount.getMountpath());
//		 if (mount.getSubpath() != null && !mount.getSubpath().isEmpty())
//		 {
//		 vmountBuilder.withSubPath(mount.getSubpath());
//		 }
//		 vms.add(vmountBuilder.build());
//		 }
//		 containerBuilder = containerBuilder.withVolumeMounts(vms);
//		 }
//		
//		 podSpecBuilder =
//		 podSpecBuilder.withContainers(containerBuilder.build());
//		
//		 Deployment.Jobs.Orderedlist.Job.Volumes volumes =
//		 jobConfig.getVolumes();
//		 if (volumes != null && volumes.getVolume() != null &&
//		 !volumes.getVolume().isEmpty()) {
//		 List volumeList = volumes.getVolume();
//		 List pvList = new ArrayList<>();
//		 for (Deployment.Jobs.Orderedlist.Job.Volumes.Volume volume :
//		 volumeList) {
//		 VolumeBuilder vlBuilder = new
//		 VolumeBuilder().withName(volume.getName())
//		 .withPersistentVolumeClaim(new
//		 PersistentVolumeClaimVolumeSourceBuilder()
//		 .withClaimName(volume.getPvc()).build());
//		 pvList.add(vlBuilder.build());
//		 }
//		
//		 podSpecBuilder = podSpecBuilder.withVolumes(pvList);
//		 }
//		
//		 podSpecBuilder = podSpecBuilder.withRestartPolicy("OnFailure")
//		 .addNewImagePullSecret("admin.registrykey");
//		
//		 Pod jobPod = podBuilder.withSpec(podSpecBuilder.build()).build();
//		
//		 logger.info("Complete the build of job pod spec");
//		 client.pods().inNamespace(targetNS).create(jobPod);
//	}

	private static void log(String action, Object obj) {
		logger.info("{}: {}", action, obj);
	}

	private static void log(String action) {
		logger.info(action);
	}
}