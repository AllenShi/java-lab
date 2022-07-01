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
@Table(name = "JOB_HISTORY")
public class JobHistory extends AbstractJobModel {
	
	@Column(name = "JOB_NAME")
	private String name;
	
	@Column(name = "JOB_DESC")
	private String description;
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_START_TIME", nullable = false)
    private Date startTime = new Date();  
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_END_TIME", nullable = false)
    private Date endTime = new Date();
	
	@Column(name = "JOB_STATE")
    private JobState jobState;
	
	@Column(name = "JOB_CMD", nullable = true)
	private String command;
	
	@Column(name = "JOB_ARGS", nullable = true)
	private String arguments;
	
	@Column(name = "JOB_ENV", nullable = true)
	private String jsonEnv;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DAG_ID", nullable = false)
	private JobDAG jobDAG;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_DEF_ID")
	private JobDefinition jobDefinition;
	
}
