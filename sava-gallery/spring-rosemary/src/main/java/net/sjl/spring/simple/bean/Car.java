package net.sjl.spring.simple.bean;

public class Car {
	
	private String make;
	private int year;
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
	
	public static class CarBuilder {
		
		private String make;
		private int year;
		
		public CarBuilder make(String make) {
			this.make = make;
			return this;
		}
		
		public CarBuilder year(int year) {
			this.year = year;
			return this;
		}
		
		public Car build() {
			Car car = new Car();
			car.setMake(make);
			car.setYear(year);
			return car;
		}
		
	}
}


