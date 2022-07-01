package net.sjl.k8s.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude={"jobQueue", "jobHistories"})
@Entity
@Table(name = "JOB_DAG")
public class JobDAG extends AbstractJobModel {

	@Column(name = "JOB_DAG_NAME", nullable = false)
	private String name;
	
	@Column(name = "JOB_DAG_DESC", nullable = true)
	private String description;
	
	@Column(name = "JOB_DAG_STATE", nullable = false)
	private JobDAGState state;
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_DAG_START_TIME")
    private Date startTime = new Date(); 
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_DAG_END_TIME")
    private Date endTime = new Date(); 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDAG")
	private Set<Job> jobQueue;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDAG")
	private List<JobHistory> jobHistories;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DAG_DEF_ID", nullable = false)
	private JobDAGDefinition dagDefinition;

}
