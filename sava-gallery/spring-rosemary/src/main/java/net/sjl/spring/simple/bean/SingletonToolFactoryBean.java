package net.sjl.spring.simple.bean;

import org.springframework.beans.factory.config.AbstractFactoryBean;

public class SingletonToolFactoryBean extends AbstractFactoryBean<Tool> {
	
	private String factoryId;
	private String toolId;

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getToolId() {
		return toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

	@Override
	protected Tool createInstance() throws Exception {
		Tool tool = new Tool(toolId);
		return tool;
	}

	@Override
	public Class<?> getObjectType() {
		return Tool.class;
	}

}
