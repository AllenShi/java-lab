package net.sjl.spring.bean.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sjl.spring.simple.bean.PrototypeToolFactoryBean;
import net.sjl.spring.simple.bean.Tool;
import net.sjl.spring.simple.bean.configuration.FactoryBeanConfiguration;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FactoryBeanConfiguration.class)
public class JavaConfigFactoryBeanTest {
	
	@Autowired
	private Tool tool1;
	
	@Autowired
	private Tool tool11;
	
	@Autowired
	private Tool tool2;
	
	@Resource(name = "&tool")
	private PrototypeToolFactoryBean toolFactory1;
	
	@Resource(name = "&tool")
	private PrototypeToolFactoryBean toolFactory11;
	
	@Resource(name = "&factory")
	private PrototypeToolFactoryBean toolFactory2;
	
	@Test
	public void testTool1andFactory() {
		assertThat(toolFactory1.getFactoryId(), equalTo("p1"));
		assertThat(toolFactory1.getToolId(), equalTo(tool1.getId()));
	}
	
	@Test
	public void testTool11andFactory() {
		assertThat(toolFactory11.getFactoryId(), equalTo("p1"));
		assertThat(toolFactory11.getToolId(), equalTo(tool11.getId()));
	}
	
	@Test
	public void testTool2andFactory() {
		assertThat(toolFactory2.getFactoryId(), equalTo("p2"));
		assertThat(toolFactory2.getToolId(), equalTo(tool2.getId()));
	}
	
	public void testEquality() {
		assert(tool1 != tool11);
		assert(toolFactory1 == toolFactory11);
		assert(toolFactory1 != toolFactory2);
	}

}
