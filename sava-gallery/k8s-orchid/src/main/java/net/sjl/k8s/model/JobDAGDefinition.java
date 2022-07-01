package net.sjl.k8s.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude={"edges"})
@Entity
@Table(name = "JOB_DAG_DEFINITION")
public class JobDAGDefinition extends AbstractJobModel {
	
	@Column(name = "JOB_DAG_NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name = "JOB_DAG_DESC", nullable = true)
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDAGDefinition")
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "jobDAGDefinition")
	private Set<JobDAGEdgeDefinition> edges = new HashSet<>();

}
