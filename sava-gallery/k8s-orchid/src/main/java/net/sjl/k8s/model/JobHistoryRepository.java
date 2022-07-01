package net.sjl.k8s.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHistoryRepository  extends JpaRepository<JobHistory, String> {

}
