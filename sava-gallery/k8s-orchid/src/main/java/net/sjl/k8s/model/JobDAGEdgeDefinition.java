package net.sjl.k8s.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "JOB_DAG_EDGE_DEFINITION")
public class JobDAGEdgeDefinition extends AbstractJobModel {
	
	@OneToOne
	@JoinColumn(name = "JOB_DAG_EDGE_DEF_SRC_ID")
	private JobDefinition sourceJob;
	
	@OneToOne
	@JoinColumn(name = "JOB_DAG_EDGE_DEF_TGT_ID")
	private JobDefinition targetJob;
	
	@ManyToOne(fetch = FetchType.LAZY)
	// @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_DAG_DEF_ID", nullable = false)
	private JobDAGDefinition jobDAGDefinition;
	

}
