package net.sjl.spring.simple.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Person {
	
	private Car car;
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	public Car getCar() {
		 return car;
	}

}
