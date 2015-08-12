package org.adorsys.adcatal.rest.integ;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchResult;
import org.adorsys.adcatal.rest.integ.utils.RestAssuredITConfig;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class CatalPicMappingEndpointIT extends RestAssuredITConfig {
	
	private static final String PATH = "/catalpicmappings";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	
	
	@Test
	public void testCreate(){
		CatalPicMapping catalPicMapping = createCatalPicMapping(); 
		CatalPicMapping catalPicMapping2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalPicMapping).post(PATH).as(CatalPicMapping.class);
		assertThat(catalPicMapping2, is(notNullValue()));
		assertThat("Created Object CatalPicMapping must have non empty id", StringUtils.isNotBlank(catalPicMapping2.getId()));
		assertThat("Created Object CatalPicMapping must be equals", catalPicMapping.contentEquals(catalPicMapping2));
	}
	
	
	@Test
	public void testUpdate(){
		CatalPicMapping catalPicMapping = getCatalPicMapping();
		assertThat(catalPicMapping, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalPicMapping.getId()));
		
		CatalPicMapping catalPicMapping2 = updateCatalPicMapping(catalPicMapping);
		CatalPicMapping catalPicMapping3 = RestAssured.given(REQUEST_SPEC).body(catalPicMapping2).put(PATH +"/"+catalPicMapping2.getId()).as(CatalPicMapping.class);
		assertThat("Updated object CatalPicMapping must not have empty id", StringUtils.isNotBlank(catalPicMapping3.getId()));
		assertThat("Updated object must CatalPicMapping be equals to test object", catalPicMapping2.contentEquals(catalPicMapping3));
	}
	
	
	@Test
	public void testSearch(){
		CatalPicMapping catalPicMapping = getCatalPicMapping();
		assertThat(catalPicMapping, is(notNullValue()));
		assertThat("Object CatalPicMapping must have non empty id", StringUtils.isNotBlank(catalPicMapping.getId()));
		
		CatalPicMappingSearchInput catalPicMappingSearchInput = RestAssuredITConfig.createCatalPicMappingSearchInput(catalPicMapping);
		CatalPicMappingSearchResult catalPicMappingSearchResult = RestAssured.given(REQUEST_SPEC).body(catalPicMappingSearchInput).post(PATH+"/findCustom").as(CatalPicMappingSearchResult.class);
		CatalPicMapping catalPicMapping2 = catalPicMappingSearchResult.getResultList().iterator().next();
		assertThat(catalPicMapping2, is(notNullValue()));
		assertThat("Return id Objec CatalPicMapping is not null", StringUtils.isNotBlank(catalPicMapping2.getId()));
		assertThat("Return object CatalPicMapping is equals to test object", catalPicMapping.contentEquals(catalPicMapping2));
		
	} 
	
	
	
	@Test
	public void testList(){
		List<CatalPicMapping> catalPicMappings = getCatalPicMappings();
		assertThat("List Object is not empty", CollectionUtils.isNotEmpty(catalPicMappings));
		int size = catalPicMappings.size();
		int count=20;
		assertThat(count, equalTo(size));
	}
	
	
	@Test
	public void testDelete(){
		CatalPicMapping catalPicMapping = getCatalPicMapping();
		assertThat(catalPicMapping, is(notNullValue()));
		assertThat("Object CatalPicMapping must have non empty id", StringUtils.isNotBlank(catalPicMapping.getId()));
		Response response = RestAssured.expect().statusCode(HttpStatus.SC_NOT_FOUND).when().given().contentType(ContentType.JSON).delete(PATH+"/"+catalPicMapping.getId());
		assertThat(response.statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
	}
	
	
	
	
	private CatalPicMapping createCatalPicMapping(){
		 CatalPicMapping catalPicMapping = new CatalPicMapping();
		 catalPicMapping.setCode("00001");
		 catalPicMapping.setPriority(1);
		 return catalPicMapping;
	}
	
	@SuppressWarnings("unchecked")
	private CatalPicMapping getCatalPicMapping(){
		Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
		CoreAbstIdentifObjectSearchResult<CatalPicMapping> response =  RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalPicMapping> resultList = response.getResultList();
    	CatalPicMapping catalPicMapping = resultList.get(1);
    	return catalPicMapping;
	}
	
	@SuppressWarnings("unchecked")
	private List<CatalPicMapping> getCatalPicMappings(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalPicMapping> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalPicMapping> resultList = response.getResultList();
    	return resultList;
    }
	
	private CatalPicMapping updateCatalPicMapping(CatalPicMapping catalPicMapping){
		catalPicMapping.setPriority(3);
		catalPicMapping.setCode("8086515");
		return catalPicMapping;
	}
	
	
	
	

}
