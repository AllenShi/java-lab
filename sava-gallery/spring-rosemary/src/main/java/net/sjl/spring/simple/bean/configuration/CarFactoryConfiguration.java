package net.sjl.spring.simple.bean.configuration;

import java.util.function.Function;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import net.sjl.spring.simple.bean.Car;
import net.sjl.spring.simple.bean.Person;
import net.sjl.spring.simple.bean.factory.CarFactoryBean;

@Configuration
public class CarFactoryConfiguration {
	
	@Bean
	public CarFactoryBean carFactoryBean() {
		CarFactoryBean factory = new CarFactoryBean();
		factory.setMake("China");
		factory.setYear(2018);
		return factory;
	}
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CarFactoryBean newCarFactoryBean(String make, int year) {
		CarFactoryBean factory = new CarFactoryBean();
		factory.setMake(make);
		factory.setYear(year);
		return factory;
	}
	
	@Bean
	public Function<String, Function<Integer, CarFactoryBean>> newCarFactoryBeanFunction() {
		return make -> (year -> {
			CarFactoryBean factory = new CarFactoryBean();
			factory.setMake(make);
			factory.setYear(year);
			return factory;
		});
	}
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Person person() {
		Person person = new Person();
		try {
			person.setCar(carFactoryBean().getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

}
