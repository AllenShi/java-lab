package net.sjl.alg.misc;

import org.junit.Test;

public class ReversedIntegerTest {
	
	@Test
	public void testSmallInteger() {
		int x = 123;
		int result = ReversedInteger.reverse(x);
		System.out.format("The x is %d and reversed is %d\n", x, result);
		
	}
	
	@Test
	public void testSmallNegtiveInteger() {
		int x = -123;
		int result = ReversedInteger.reverse(x);
		System.out.format("The x is %d and reversed is %d\n", x, result);
		
	}
	
	@Test
	public void testOverflowInteger() {
		int x = 1534236469;
		int result = ReversedInteger.reverse(x);
		System.out.format("The x is %d and reversed is %d\n", x, result);
		
	}

}
