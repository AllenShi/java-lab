package net.sjl.fragrans.test.yaml.bean;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.sjl.fragrans.yaml.bean.Property;

public class DeserializedPropertyTest {

	@Test
	public void testCustomizedDeserializedNonPasswordProperty() {

		String json = "{\"dataType\" : \"string\", \"value\" : \"abc\" }";
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.readValue(json, Property.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCustomizedDeserializedPasswordProperty() {

		String json = "{\"dataType\" : \"password\", \"value\" : \"mypass\" }";
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.readValue(json, Property.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
