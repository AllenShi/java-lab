package net.sjl.spring.simple.bean.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.sjl.spring.simple.bean.PrototypeToolFactoryBean;
import net.sjl.spring.simple.bean.Tool;

@Configuration
@ComponentScan(basePackages = "net.sjl.spring.bean")
public class FactoryBeanConfiguration {
	
	@Bean(name = "tool")
	public PrototypeToolFactoryBean factoryBean1() {
		PrototypeToolFactoryBean factory = new PrototypeToolFactoryBean();
		factory.setFactoryId("p1");
		factory.setToolId("t1");
		return factory;
	}
	
	@Bean(name = "factory")
	public PrototypeToolFactoryBean factoryBean2() {
		PrototypeToolFactoryBean factory = new PrototypeToolFactoryBean();
		factory.setFactoryId("p2");
		factory.setToolId("t2");
		return factory;
	}
	
	
	@Bean
	public Tool tool1() throws Exception {
		try {
			PrototypeToolFactoryBean factory = factoryBean1();
			System.out.format("factory in tool1 is %s and class name is {} \n", factory, factory.getClass());
			return factory.getObject();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Bean
	public Tool tool11() throws Exception {
		try {
			PrototypeToolFactoryBean factory = factoryBean1();
			System.out.format("factory in tool11 is %s and class name is {} \n", factory, factory.getClass());
			return factory.getObject();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Bean
	public Tool tool2() throws Exception {
		try {
			return factoryBean2().getObject();
		} catch (Exception e) {
			throw e;
		}
	}

}
  