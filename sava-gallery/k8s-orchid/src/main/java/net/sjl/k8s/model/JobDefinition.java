package net.sjl.k8s.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "JOB_DEFINITION")
public class JobDefinition extends AbstractJobModel {

	@Column(name = "JOB_DEF_NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name = "JOB_DEF_DESC", nullable = true)
	private String description;
	
	@Column(name = "JOB_DEF_IMG_URL", nullable = false)
	private String imageUrl;
	
	@Column(name = "JOB_DEF_CMD", nullable = true)
	private String command;
	
	@Column(name = "JOB_DEF_LABELS")
	private String labels;
	
	@Column(name = "JOB_DEF_ARGS", nullable = true)
	private String arguments;
	
	@Column(name = "JOB_DEF_ENV", nullable = true)
	private String jsonEnv;
	
	@Column(name = "JOB_DEF_IS_START", nullable = false)
	private boolean isStartJob;

}
