package net.sjl.k8s.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDAGDefinitionRepository extends JpaRepository<JobDAGDefinition, String> {
	
	@Query("SELECT jdd FROM JobDAGDefinition jdd JOIN FETCH jdd.edges WHERE jdd.id = :id")
	public Optional<JobDAGDefinition> findByIdWithEdges(@Param("id") String id);

}
