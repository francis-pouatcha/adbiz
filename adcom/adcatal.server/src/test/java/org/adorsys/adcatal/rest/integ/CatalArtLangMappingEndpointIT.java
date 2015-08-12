package org.adorsys.adcatal.rest.integ;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalArtLangMappingSearchResult;
import org.adorsys.adcatal.rest.integ.utils.RestAssuredITConfig;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
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

public class CatalArtLangMappingEndpointIT extends RestAssuredITConfig {
	
	private static final String PATH = "/catalartfeatmappings";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	
	
	@Test
	public void testCreate(){
		CatalArtLangMapping catalArtLangMapping = createCatalArtLangMapping(); 
		CatalArtLangMapping catalArtLangMapping2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArtLangMapping).post(PATH).as(CatalArtLangMapping.class);
		assertThat(catalArtLangMapping2, is(notNullValue()));
		assertThat("Created Object CatalArtLangMapping must have non empty id", StringUtils.isNotBlank(catalArtLangMapping2.getId()));
		assertThat("Created Object CatalArtLangMapping must be equals", catalArtLangMapping.contentEquals(catalArtLangMapping2));
	}
	
	
	@Test
	public void testUpdate(){
		CatalArtLangMapping catalArtLangMapping = getCatalArtLangMapping();
		assertThat(catalArtLangMapping, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalArtLangMapping.getId()));
		
		CatalArtLangMapping catalArtLangMapping2 = updateCatalArtLangMapping(catalArtLangMapping);
		CatalArtLangMapping catalArtLangMapping3 = RestAssured.given(REQUEST_SPEC).body(catalArtLangMapping2).put(PATH +"/"+catalArtLangMapping2.getId()).as(CatalArtLangMapping.class);
		assertThat("Updated object CatalArtLangMapping must not have empty id", StringUtils.isNotBlank(catalArtLangMapping3.getId()));
		assertThat("Updated object must CatalArtLangMapping be equals to test object", catalArtLangMapping2.contentEquals(catalArtLangMapping3));
	}
	
	
	@Test
	public void testSearch(){
		CatalArtLangMapping catalArtLangMapping = getCatalArtLangMapping();
		assertThat(catalArtLangMapping, is(notNullValue()));
		assertThat("Object CatalArtLangMapping must have non empty id", StringUtils.isNotBlank(catalArtLangMapping.getId()));
		
		CatalArtLangMappingSearchInput catalArtLangMappingSearchInput = RestAssuredITConfig.createCatalArtLangMappingSearchInput(catalArtLangMapping);
		CatalArtLangMappingSearchResult catalArtLangMappingSearchResult = RestAssured.given(REQUEST_SPEC).body(catalArtLangMappingSearchInput).post(PATH+"/findCustom").as(CatalArtLangMappingSearchResult.class);
		CatalArtLangMapping catalArtLangMapping2 = catalArtLangMappingSearchResult.getResultList().iterator().next();
		assertThat(catalArtLangMapping2, is(notNullValue()));
		assertThat("Return id Objec CatalArtLangMappingt is not null", StringUtils.isNotBlank(catalArtLangMapping2.getId()));
		assertThat("Return object CatalArtLangMapping is equals to test object", catalArtLangMapping.contentEquals(catalArtLangMapping2));
		
	} 
	
	
	
	@Test
	public void testList(){
		List<CatalArtLangMapping> catalArtLangMappings = getCatalArtLangMappings();
		assertThat("List Object is not empty", CollectionUtils.isNotEmpty(catalArtLangMappings));
		int size = catalArtLangMappings.size();
		int count=20;
		assertThat(count, equalTo(size));
	}
	
	
	@Test
	public void testDelete(){
		CatalArtLangMapping catalArtLangMapping = getCatalArtLangMapping();
		assertThat(catalArtLangMapping, is(notNullValue()));
		assertThat("Object CatalArtLangMapping must have non empty id", StringUtils.isNotBlank(catalArtLangMapping.getId()));
		Response response = RestAssured.expect().statusCode(HttpStatus.SC_NOT_FOUND).when().given().contentType(ContentType.JSON).delete(PATH+"/"+catalArtLangMapping.getId());
		assertThat(response.statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
	}
	
	
	
	
	private CatalArtLangMapping createCatalArtLangMapping(){
		CatalArtLangMapping catalArtLangMapping = new CatalArtLangMapping();
		catalArtLangMapping.setArtName("RHINOSTOP CPR B/20");
		catalArtLangMapping.setShortName("RHI");
		catalArtLangMapping.setLangIso2("fr");
		catalArtLangMapping.setIdentif(RandomStringUtils.randomAlphanumeric(7).toUpperCase());
		return catalArtLangMapping;
	}
	
	@SuppressWarnings("unchecked")
	private CatalArtLangMapping getCatalArtLangMapping(){
		Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
		CoreAbstIdentifObjectSearchResult<CatalArtLangMapping> response =  RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArtLangMapping> resultList = response.getResultList();
    	CatalArtLangMapping catalArtLangMapping = resultList.get(1);
    	return catalArtLangMapping;
	}
	
	@SuppressWarnings("unchecked")
	private List<CatalArtLangMapping> getCatalArtLangMappings(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalArtLangMapping> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArtLangMapping> resultList = response.getResultList();
    	return resultList;
    }
	
	private CatalArtLangMapping updateCatalArtLangMapping(CatalArtLangMapping catalArtLangMapping){
		catalArtLangMapping.setArtName("Carbocisteine");
		catalArtLangMapping.setLangIso2("en");
		return catalArtLangMapping;
	}
	
	
//	private CatalPicMapping createCatalPicMapping(CatalArticle catalArticle){
//		 CatalPicMapping catalPicMapping = new CatalPicMapping();
//		 catalPicMapping.setCode("00001");
//		 catalPicMapping.setPriority(1);
//		 catalPicMapping.setCntnrIdentif(catalArticle.getIdentif());
//		 return catalPicMapping;
//	}
	
	
	

}
