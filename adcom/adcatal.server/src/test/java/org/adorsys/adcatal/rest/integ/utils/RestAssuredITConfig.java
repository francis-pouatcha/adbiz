package org.adorsys.adcatal.rest.integ.utils;

import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;

/**
 * Base Class for RestAssured Tests
 * @author fhi@adorsys.de
 */
public class RestAssuredITConfig {
	
	@BeforeClass
	public static void init() {
		String host = System.getProperty("host");
		String port = System.getProperty("port");
		
		RestAssured.baseURI = String.format("http://%s", StringUtils.isBlank(host) ? "localhost" : host);
		RestAssured.port = StringUtils.isBlank(port) ? 8080 : Integer.valueOf(port);
		RestAssured.basePath = "/adcatal.server/rest";
		
		// register ObjectId Serializer
//		RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
//		new Jackson2ObjectMapperFactory() {
//		        @Override
//		        @SuppressWarnings("rawtypes")
//		        public ObjectMapper create(Class clazz, String charset) {
//		            ObjectMapper objectMapper = new ObjectMapper();
//		            // CUstomize object mapper
//		            return objectMapper;
//		        }
//		    }
//		))
//		// and force UTF-8
//		.encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"))
//		.decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
		
		Logger.getLogger(RestAssuredITConfig.class.getName()).info("Initialized RestAssured for "+RestAssured.baseURI+":"+RestAssured.port+RestAssured.basePath);
	}
	
}
