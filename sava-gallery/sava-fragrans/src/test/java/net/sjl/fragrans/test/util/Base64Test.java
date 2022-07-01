package net.sjl.fragrans.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sjl.fragrans.util.Base64Codec;

public class Base64Test {

	@Test
	public void testBasicCodec() {
		String password = "AB.DFDFD";
		System.out.format("The password is %s\n", password);
		String encoded = Base64Codec.encode(password);
		System.out.format("The encode is %s\n", encoded);
		String decoded = Base64Codec.decode(encoded);
		
		System.out.format("The decode is %s\n", decoded);

		assertEquals(password, decoded);

	}
	
	@Test
	public void testPlainCodec() {
		String input = "\\{base64}\\abc";
		System.out.format("The input is %s\n", input);
		String decoded = Base64Codec.decode(input);
		
		System.out.format("The decode is %s\n", decoded);

		assertEquals(input, decoded);

	}
	
	@Test
	public void testEsc1Codec() {
		String password = "{base64}abc\\{base64}\\";
		String escPassword = "\\{base64}\\abc\\{base64}\\";
		System.out.format("The password is %s\n", escPassword);
		String encoded = Base64Codec.encode(escPassword);
		System.out.format("The encode is %s\n", encoded);
		String decoded = Base64Codec.decode(encoded);
		
		System.out.format("The decode is %s\n", decoded);
		assertEquals(password, decoded);
	}
	
	@Test
	public void testEsc3Codec() {
		String password = "\\\\{base64}\\\\abc";
		System.out.format("The password is %s\n", password);
		String encoded = Base64Codec.encode(password);
		System.out.format("The encode is %s\n", encoded);
		String decoded = Base64Codec.decode(encoded);
		
		System.out.format("The decode is %s\n", decoded);

		assertEquals(password, decoded);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEsc4Codec() {
		String password = "{base64}\\\\{base64}\\\\abc";
		System.out.format("The password is %s\n", password);
		String encoded = Base64Codec.encode(password);
		System.out.format("The encode is %s\n", encoded);
		String decoded = Base64Codec.decode(encoded);
		
		System.out.format("The decode is %s\n", decoded);

		assertEquals(password, decoded);
	}


}
