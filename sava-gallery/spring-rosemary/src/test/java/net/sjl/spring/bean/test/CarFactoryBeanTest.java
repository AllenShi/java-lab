package net.sjl.spring.bean.test;

import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import net.sjl.spring.simple.bean.Car;
import net.sjl.spring.simple.bean.Person;
import net.sjl.spring.simple.bean.configuration.CarFactoryConfiguration;
import net.sjl.spring.simple.bean.factory.CarFactoryBean;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {CarFactoryConfiguration.class})
public class CarFactoryBeanTest {
	
	@Autowired
	Person person1;
	
	@Autowired
	Person person2;
	
	@Autowired
	AnnotationConfigApplicationContext context;
	
	@Autowired
	Function<String, Function<Integer, CarFactoryBean>> newCarFactoryBeanFunction;
	
	@Test
	public void testPerson1() {
		Car car1 = person1.getCar();
		System.out.println("car1 -> " + car1);
		System.out.println("person1 car make -> " + car1.getMake());
		System.out.println("person1 car year -> " + car1.getYear());
	}
	
	@Test
	public void testPerson2() {
		Car car2 = person2.getCar();
		System.out.println("car2 -> " + car2);
		System.out.println("person2 car make -> " + car2.getMake());
		System.out.println("person2 car year -> " + car2.getYear());
	}
	
	@Test
	public void testPerson3() {
		Person person3 = (Person)context.getBean("person");
		Car car3 = person3.getCar();
		System.out.println("person3 -> " + person3);
		System.out.println("car3 -> " + car3);
		System.out.println("person3 ca3 make -> " + car3.getMake());
		System.out.println("person3 car3 year -> " + car3.getYear());
		
		Person person4 = (Person)context.getBean("person");
		Car car4 = person4.getCar();
		System.out.println("person4 -> " + person4);
		System.out.println("car4 -> " + car4);
		System.out.println("person4 car4 make -> " + car4.getMake());
		System.out.println("person4 car4 year -> " + car4.getYear());
	}
	
	@Test
	public void testCarFactoryBean() throws Exception {
		Car car5 = (Car)context.getBean("newCarFactoryBean", "USA", 2000);
		System.out.println("car5 -> " + car5);
		System.out.println("car5 make -> " + car5.getMake());
		System.out.println("car5 year -> " + car5.getYear());
		
		Car car6 = (Car)context.getBean("newCarFactoryBean", "Euro", 2009);
		System.out.println("car6 -> " + car6);
		System.out.println("car6 make -> " + car6.getMake());
		System.out.println("car6 year -> " + car6.getYear());
	}
	
	@Test
	public void testFunctionalInterface() {
		CarFactoryBean car7 = newCarFactoryBeanFunction.apply("France").apply(2002);
		System.out.println("car7 -> " + car7);
		System.out.println("car7 make -> " + car7.getMake());
		System.out.println("car7 year -> " + car7.getYear());
	}
}
