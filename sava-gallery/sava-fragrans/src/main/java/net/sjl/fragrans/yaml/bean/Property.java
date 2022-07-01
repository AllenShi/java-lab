package net.sjl.fragrans.yaml.bean;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.sjl.fragrans.util.Base64Codec;

@JsonDeserialize(using = Property.PropertyJsonDeserializer.class)
public class Property {
	
	private String dataType;
	private String value;
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "data type is " + dataType + ", value is " + value;
	}
	
	public static class PropertyJsonDeserializer extends JsonDeserializer<Property> {

		@Override
		public Property deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			
			InnerProperty innerProperty = p.readValueAs(InnerProperty.class);
	        return innerProperty.toProperty();
		}
	}
	
	private static class InnerProperty {
		
		public String dataType;
		public String value;
		
		private Property toProperty() {
			Property property = new Property();
			property.setDataType(dataType);
			property.setValue(encodeValue(value, dataType));
			return property;
		}
		
		private String encodeValue(String value, String dataType) {
			if(dataType.equalsIgnoreCase("password")) {
				return Base64Codec.encode(value);
			} 
			
			return value;
		}
	}

}
