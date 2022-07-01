package net.sjl.k8s.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
@Entity
@Table(name = "JOB")
public class Job extends AbstractJobModel {
	
	@Column(name = "JOB_NAME")
	private String name;
	
	@Column(name = "JOB_DESC")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DAG_ID", nullable = false)
	private JobDAG jobDAG;
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_START_TIME")
    private Date startTime = new Date();  
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DEF_ID")
	private JobDefinition jobDefinition;
	
	@Column(name = "JOB_STATE")
    private JobState jobState;
	
	@Column(name = "JOB_CMD", nullable = true)
	private String command;
	
	@Column(name = "JOB_ARGS", nullable = true)
	private String arguments;
	
	@Column(name = "JOB_ENV", nullable = true)
	private String jsonEnv;
}
