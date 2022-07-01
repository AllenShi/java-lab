package net.sjl.fragrans.test.yaml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import net.sjl.fragrans.yaml.JacksonYAML;

public class YAMLTest {
	
	@Test
	public void testJacksonYAMLReader() {
		String yamlValue = "key1: value1\n"
		         + "key2: value2\n"
		         + "key3s:\n"
		         + "  - 1\n"
		         + "  - 2\n"
		         + "key4:\n"
		         + "  sub4:\n"
		         + "    subsub4: value4";
		
		JacksonYAML.readYAML(yamlValue);
	}
	
	@Test
	public void testJacksonYAMLReader1()  {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example1.yml").getFile());
		
		try {
			JacksonYAML.readYAML(new BufferedInputStream(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJacksonYAMLReader2() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example2.yml").getFile());
		
		try {
			JacksonYAML.readYAML(new BufferedInputStream(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJacksonYAMLReaderPath() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("example2.yml").getFile());
		
		String yamlPath = "/shared_configurations/ECC_BASE_DIR";
		String value = "";
		try {
			value = JacksonYAML.readYAMLPath(new BufferedInputStream(new FileInputStream(file)), yamlPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Path -> " + yamlPath + ", value -> " + value);
	}


}
