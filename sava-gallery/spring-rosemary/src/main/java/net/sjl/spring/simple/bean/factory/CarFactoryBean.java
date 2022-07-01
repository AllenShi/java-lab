package net.sjl.spring.simple.bean.factory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.Assert;

import net.sjl.spring.simple.bean.Car;

public class CarFactoryBean implements FactoryBean<Car> {

	private String make;
	private int year;

	@PostConstruct
	public void setup() throws Throwable {
		// these methods throw an exception that
		// will arrest construction if the assertions aren't met
		System.out.println("post init --------> " + this);
		Assert.notNull(this.make, "the 'make' must not be null");
		Assert.isTrue(this.year > 0, "the 'year' must be a valid value");
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public Car getObject() throws Exception {
		Car car = new Car.CarBuilder().make(make).year(year).build();
		return car;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Car.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
