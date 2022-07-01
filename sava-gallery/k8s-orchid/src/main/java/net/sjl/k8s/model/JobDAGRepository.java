package net.sjl.k8s.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobDAGRepository extends JpaRepository<JobDAG, String> {

}
