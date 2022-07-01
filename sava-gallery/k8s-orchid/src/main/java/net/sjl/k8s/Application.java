package net.sjl.k8s;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.sjl.k8s.model.JobDAGDefinition;
import net.sjl.k8s.model.JobDAGDefinitionRepository;
import net.sjl.k8s.model.JobDAGEdgeDefinition;
import net.sjl.k8s.model.JobDefinition;
import net.sjl.k8s.model.JobDefinitionRepository;

@SpringBootApplication
public class Application {

	@Autowired
	private JobDefinitionRepository jobDefinitionRepository;

	@Autowired
	private JobDAGDefinitionRepository jobDAGDefinitionRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			constructJobDAGDefinition();

		};
	}

	private void constructJobDAGDefinition() {
		JobDefinition startDef = new JobDefinition();
		startDef.setName("Start");
		startDef.setImageUrl("perl");
		startDef.setCommand("perl -Mbignum=bpi -wle print bpi(2000)");
		startDef.setDescription("Start Job for All");
		startDef.setStartJob(true);
		jobDefinitionRepository.save(startDef);

		JobDefinition jobDef1 = new JobDefinition();
		jobDef1.setName("Provision_ECM");
		jobDef1.setImageUrl("perl");
		jobDef1.setCommand("perl -Mbignum=bpi -wle print bpi(2000)");
		jobDef1.setDescription("Provision ECM");
		jobDefinitionRepository.save(jobDef1);

		JobDefinition jobDef2 = new JobDefinition();
		jobDef2.setName("Configuration_ECM");
		jobDef2.setImageUrl("perl");
		jobDef2.setCommand("perl -Mbignum=bpi -wle print bpi(2000)");
		jobDef2.setDescription("Configure ECM");
		jobDefinitionRepository.save(jobDef2);

		JobDefinition jobDef3 = new JobDefinition();
		jobDef3.setName("Validation_ECM");
		jobDef3.setImageUrl("perl");
		jobDef3.setCommand("perl -Mbignum=bpi -wle print bpi(2000)");
		jobDef3.setDescription("Validate ECM");
		jobDefinitionRepository.save(jobDef3);

		Iterable<JobDefinition> jobDefinitionList = jobDefinitionRepository.findAll();
		Iterator<JobDefinition> jobDefinitionItor = jobDefinitionList.iterator();
		while (jobDefinitionItor.hasNext()) {
			JobDefinition jobDefinition = jobDefinitionItor.next();
			System.out.format("[job id] : %s, [job name] : %s, [job desc] : %s\n", jobDefinition.getId(),
					jobDefinition.getName(), jobDefinition.getDescription());
		}

		JobDAGDefinition jobDAGDef1 = new JobDAGDefinition();
		jobDAGDef1.setName("ECM_JOB_DAG");
		jobDAGDef1.setDescription("ECM JOB DAG");

		JobDAGEdgeDefinition edge1 = new JobDAGEdgeDefinition();
		edge1.setSourceJob(startDef);
		edge1.setTargetJob(jobDef1);
		edge1.setJobDAGDefinition(jobDAGDef1);

		JobDAGEdgeDefinition edge2 = new JobDAGEdgeDefinition();
		edge2.setSourceJob(jobDef1);
		edge2.setTargetJob(jobDef2);
		edge2.setJobDAGDefinition(jobDAGDef1);

		JobDAGEdgeDefinition edge3 = new JobDAGEdgeDefinition();
		edge3.setSourceJob(jobDef2);
		edge3.setTargetJob(jobDef3);
		edge3.setJobDAGDefinition(jobDAGDef1);

		// Set<JobDAGEdgeDefinition> edges = new HashSet<>();
		// edges.add(edge1);
		// edges.add(edge2);
		// edges.add(edge3);
		// jobDAGDef1.setEdges(edges);
		jobDAGDef1.getEdges().add(edge1);
		jobDAGDef1.getEdges().add(edge2);
		jobDAGDef1.getEdges().add(edge3);
		jobDAGDefinitionRepository.save(jobDAGDef1);

		Iterator<JobDAGDefinition> jobDAGDefItor = jobDAGDefinitionRepository.findAll().iterator();
		while (jobDAGDefItor.hasNext()) {
			JobDAGDefinition jobDAGDef = jobDAGDefItor.next();
			System.out.println("jobDAGDef --> id: " + jobDAGDef.getId() + ", name: " + jobDAGDef.getName()
					+ ", description: " + jobDAGDef.getDescription());
			Optional<JobDAGDefinition> jobDAGDefDetail = jobDAGDefinitionRepository
					.findByIdWithEdges(jobDAGDef.getId());
			if (jobDAGDefDetail.isPresent()) {
				Set<JobDAGEdgeDefinition> dagEdges = jobDAGDefDetail.get().getEdges();
				for (JobDAGEdgeDefinition edge : dagEdges) {
					System.out.println("dagEdges --> source job: " + edge.getSourceJob().getName() + ", target job : "
							+ edge.getTargetJob().getName());
				}
			}
		}
	}
}
