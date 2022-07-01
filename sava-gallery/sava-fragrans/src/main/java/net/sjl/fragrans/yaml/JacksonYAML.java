package net.sjl.fragrans.yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class JacksonYAML {
	
	public static void readYAML(String yamlValue) {
		
		readYAML(new ByteArrayInputStream(yamlValue.getBytes(StandardCharsets.UTF_8)));
	}
	
	public static void readYAML(InputStream istream) {
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
		TypeReference<Map<String, Object>> typeRef 
		  = new TypeReference<Map<String, Object>>() {};
		try {
			Map<String, Object> result = mapper.readValue(istream, typeRef);
			if(result != null) {
				for(String key : result.keySet()) {
					Object v = result.get(key);
					recursiveDisplay(key, v);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void recursiveDisplay(String key, Object v) {
		if(v instanceof Object[]) {
			for(Object o : (Object[])v) {
				recursiveDisplay(key, o);
			}
		} else if(v instanceof Map) {
			for(String k : ((Map<String, Object>)v).keySet()) {
				recursiveDisplay(key + "." + k,  ((Map<String, Object>)v).get(k));
			}
		} else {
			System.out.println("key -> " + key + ", value -> " + v);
		}
	}
	
   public static String readYAMLPath(InputStream istream, String pathExp) {
		
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
		JsonNode node;
		try {
			node = mapper.readTree(istream);
			JsonNode tNode = node.at(pathExp);
			return tNode.asText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static void writeYAML() {
		
	}

}
