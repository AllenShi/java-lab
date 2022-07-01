package net.sjl.fragrans.test.re;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegularExpressionTestCase {
	
	@Test
	public void testFullMatch() {
		String text = "{{ sharedConfiguration.SC_ECC_BASE_DIR }}/logs/ecc-k8s";
		String expression = "\\{\\{\b*([^\b]+)\b*\\}\\}(.*)";
		Pattern patern = Pattern.compile(expression);
		Matcher matcher = patern.matcher(text);
		if(matcher.matches()) {
			String found1 = matcher.group(1);
			System.out.println("Found 1 is " + found1);
			String found2 = matcher.group(2);
			System.out.println("Found 2 is " + found2);
		}
	}
	
	@Test
	public void testNullMatch() {
		String text = "{{ sharedConfiguration.SC_ECC_BASE_DIR }}";
		String expression = "\\{\\{\b*([^\b]+)\b*\\}\\}(.*)";
		Pattern patern = Pattern.compile(expression);
		Matcher matcher = patern.matcher(text);
		if(matcher.matches()) {
			String found1 = matcher.group(1);
			System.out.println("Found 1 is " + found1);
			String found2 = matcher.group(2);
			System.out.println("Found 2 is " + found2);
		}
	}

}
