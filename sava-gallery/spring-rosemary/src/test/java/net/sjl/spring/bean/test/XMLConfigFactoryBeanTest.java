package net.sjl.spring.bean.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sjl.spring.simple.bean.SingletonToolFactoryBean;
import net.sjl.spring.simple.bean.Tool;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-factory-bean-ctx.xml"})
public class XMLConfigFactoryBeanTest {
	
	@Resource(name = "singleTool")
	private Tool tool1;
	
	@Resource(name = "singleTool")
	private Tool tool2;
	
	@Resource(name = "&singleTool")
	private SingletonToolFactoryBean factory;
	
	@Test
	public void testTool1() {
		assertThat(factory.getFactoryId(), equalTo("s1"));
		assertThat(factory.getToolId(), equalTo(tool1.getId()));
	}
	
	@Test
	public void testTool2() {
		assertThat(factory.getFactoryId(), equalTo("s1"));
		assertThat(factory.getToolId(), equalTo(tool2.getId()));
	}
	
	@Test
	public void testToolEquality() {
		assert(tool1 == tool2);
	}

}
