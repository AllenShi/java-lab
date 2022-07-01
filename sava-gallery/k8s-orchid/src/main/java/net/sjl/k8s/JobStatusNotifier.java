package net.sjl.k8s;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.Job;
import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.Quantity;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ResourceQuota;
import io.fabric8.kubernetes.api.model.ResourceQuotaBuilder;
import io.fabric8.kubernetes.client.APIGroupNotAvailableException;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.Watcher.Action;

public class JobStatusNotifier {
	private static final Logger logger = LoggerFactory.getLogger(JobStatusNotifier.class);
	
	private static JobStatusNotifier instance = null;
	
	private List<JobStatusObserver> observers = null;
	private String jobNameSpace = "default";
	
	public JobStatusNotifier() {
		this(null);
	}
	
	public JobStatusNotifier(String jobNameSpace) {
		this.observers = new ArrayList<>();
		if(jobNameSpace != null && !jobNameSpace.isEmpty()) {
			this.jobNameSpace = jobNameSpace;
		}
	}
	
	public void watch() {
		
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
					log("Get jobs in thisisatest namespace", client.extensions().jobs().inNamespace("thisisatest").list());
				} catch (APIGroupNotAvailableException e) {
					log("Skipping jobs example - extensions API group not available");
				}
				
				Job piJob = client.extensions().jobs().inNamespace("thisisatest").
						createNew().
						withNewMetadata().withName("pi-job").addToLabels("creator", "allen").endMetadata().
						withNewSpec().
						  withNewTemplate().
						    withNewMetadata().withName("pi").endMetadata().
							withNewSpec().
							  withContainers(new ContainerBuilder().withName("pi").withImage("perl").withCommand(Arrays.asList("perl", "-Mbignum=bpi", "-wle", "print bpi(2000)")).build()).
							  withRestartPolicy("Never").
							endSpec().
						  endTemplate().
						endSpec().done();
				
				final CountDownLatch closeLatch = new CountDownLatch(1);
				client.extensions().jobs().inNamespace("thisisatest").withName("pi-job").watch(new Watcher<Job>() {
			        @Override
			        public void eventReceived(Action action, Job job) {
			          logger.info("Job eventReceived - action: {}, version: {}, startTime: {}, completionTime: {}, suceeded: {}", 
			        		  action, 
			        		  job.getMetadata().getResourceVersion(), 
			        		  job.getStatus().getStartTime(),
			        		  job.getStatus().getCompletionTime(),
			        		  job.getStatus().getSucceeded());
			          
			          Integer status = job.getStatus().getSucceeded();
			          if(status != null && status.intValue() == 1) {
			        	  logger.info("Job runs to completion with successful - action: {}, suceeded: {}",  action, status);
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
				
//				closeLatch.await(100, TimeUnit.SECONDS);
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
	
	
	private static void log(String action, Object obj) {
		logger.info("{}: {}", action, obj);
	}

	private static void log(String action) {
		logger.info(action);
	}
	

}
