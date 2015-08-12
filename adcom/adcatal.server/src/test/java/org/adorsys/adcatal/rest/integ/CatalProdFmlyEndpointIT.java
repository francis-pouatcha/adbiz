package org.adorsys.adcatal.rest.integ;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchResult;import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlySearchInput;
import org.adorsys.adcatal.jpa.CatalProdFmlySearchResult;
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

public class CatalProdFmlyEndpointIT extends RestAssuredITConfig {
	
	private static final String PATH = "/catalproductfamilys";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	@Test
	public void testCreate(){
		CatalProdFmly catalProdFmly = createCatalProdFmly(); 
		CatalProdFmly catalProdFmly2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalProdFmly).post(PATH).as(CatalProdFmly.class);
		assertThat(catalProdFmly2, is(notNullValue()));
		assertThat("Created Object catalProdFmly must have non empty id", StringUtils.isNotBlank(catalProdFmly2.getId()));
		assertThat("Created Object catalProdFmly must be equals", catalProdFmly.contentEquals(catalProdFmly2));
	}
	
	
	@Test
	public void testUpdate(){
		CatalProdFmly catalProdFmly = getCatalProdFmly();
		assertThat(catalProdFmly, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalProdFmly.getId()));
		
		CatalProdFmly catalProdFmly2 = updateCatalProdFmly(catalProdFmly);
		CatalProdFmly catalProdFmly3 = RestAssured.given(REQUEST_SPEC).body(catalProdFmly2).put(PATH +"/"+catalProdFmly2.getId()).as(CatalProdFmly.class);
		assertThat("Updated object CatalProdFmly must not have empty id", StringUtils.isNotBlank(catalProdFmly3.getId()));
		assertThat("Updated object CatalProdFmly must be equals to test object", catalProdFmly2.contentEquals(catalProdFmly3));
	}
	
	
	@Test
	public void testSearch(){
		CatalProdFmly catalProdFmly = getCatalProdFmly();
		assertThat(catalProdFmly, is(notNullValue()));
		assertThat("Object CatalProdFmly must have non empty id", StringUtils.isNotBlank(catalProdFmly.getId()));
		
		CatalProdFmlySearchInput catalProdFmlySearchInput = RestAssuredITConfig.createCatalProdFmlySearchInput(catalProdFmly);
		CatalProdFmlySearchResult catalProdFmlySearchResult = RestAssured.given(REQUEST_SPEC).body(catalProdFmlySearchInput).post(PATH+"/findCustom").as(CatalProdFmlySearchResult.class);
		CatalProdFmly catalProdFmly2 = catalProdFmlySearchResult.getResultList().iterator().next();
		assertThat(catalProdFmly2, is(notNullValue()));
		assertThat("Return id Objec CatalArtManufSupp is not null", StringUtils.isNotBlank(catalProdFmly2.getId()));
		assertThat("Return object CatalArtManufSupp is equals to test object", catalProdFmly.contentEquals(catalProdFmly2));
		
	} 
	
	
	
	@Test
	public void testList(){
		List<CatalProdFmly> catalArtManufSupps = getCatalArtManufSupps();
		assertThat("List Object is not empty", CollectionUtils.isNotEmpty(catalArtManufSupps));
		int size = catalArtManufSupps.size();
		int count=20;
		assertThat(count, equalTo(size));
	}
	
	
	@Test
	public void testDelete(){
		CatalProdFmly catalProdFmly = getCatalProdFmly();
		assertThat(catalProdFmly, is(notNullValue()));
		assertThat("Object CatalArtManufSupp must have non empty id", StringUtils.isNotBlank(catalProdFmly.getId()));
		Response response = RestAssured.expect().statusCode(HttpStatus.SC_NOT_FOUND).when().given().contentType(ContentType.JSON).delete(PATH+"/"+catalProdFmly.getId());
		assertThat(response.statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
	}
	
	
	
	
	private CatalProdFmly createCatalProdFmly(){
		 CatalProdFmly catalProdFmly = new CatalProdFmly();
		 catalProdFmly.setFamPath("/antibiotic/Ampicillin_cloxacillin/");
		 catalProdFmly.setParentIdentif("antibiotic");
		 catalProdFmly.setIdentif("Ampicillin_cloxacillin");
		 return catalProdFmly;
	}
	
	@SuppressWarnings("unchecked")
	private CatalProdFmly getCatalProdFmly(){
		Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
		CoreAbstIdentifObjectSearchResult<CatalProdFmly> response =  RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalProdFmly> resultList = response.getResultList();
    	CatalProdFmly catalProdFmly = resultList.get(1);
    	return catalProdFmly;
	}
	
	@SuppressWarnings("unchecked")
	private List<CatalProdFmly> getCatalArtManufSupps(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalProdFmly> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalProdFmly> resultList = response.getResultList();
    	return resultList;
    }
	
	private CatalProdFmly updateCatalProdFmly(CatalProdFmly catalProdFmly){
		catalProdFmly.setIdentif("Benzodiazepines");
		return catalProdFmly;
	}

}
