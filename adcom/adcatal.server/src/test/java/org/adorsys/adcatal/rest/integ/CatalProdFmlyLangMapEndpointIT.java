package org.adorsys.adcatal.rest.integ;

import org.adorsys.adcatal.rest.integ.utils.RestAssuredITConfig;
import org.junit.Test;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class CatalProdFmlyLangMapEndpointIT extends RestAssuredITConfig {
	
	private static final String PATH = "/catalfamilyfeatmapings";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	@Test
	public void testCreate(){
		
	}
	
	@Test
	public void testUpdate(){
		
	}
	
	@Test
	public void testSearch(){
			
	} 
	
	
	
	@Test
	public void testList(){
		
	}
	
	@Test
	public void testDelete(){
		
	}


}
