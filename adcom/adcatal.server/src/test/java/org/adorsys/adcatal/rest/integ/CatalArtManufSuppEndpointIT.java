package org.adorsys.adcatal.rest.integ;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchResult;
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

public class CatalArtManufSuppEndpointIT extends RestAssuredITConfig {
	
	private static final String PATH = "/catalartmanufsupps";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	
	@Test
	public void testCreate(){
		CatalArtManufSupp catalArtManufSupp = createCatalArtManufSupp(); 
		CatalArtManufSupp catalArtManufSupp2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArtManufSupp).post(PATH).as(CatalArtManufSupp.class);
		assertThat(catalArtManufSupp2, is(notNullValue()));
		assertThat("Created Object CatalArtManufSupp must have non empty id", StringUtils.isNotBlank(catalArtManufSupp2.getId()));
		assertThat("Created Object CatalArtManufSupp must be equals", catalArtManufSupp.contentEquals(catalArtManufSupp2));
	}
	
	
	@Test
	public void testUpdate(){
		CatalArtManufSupp catalArtManufSupp = getCatalArtManufSupp();
		assertThat(catalArtManufSupp, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalArtManufSupp.getId()));
		
		CatalArtManufSupp catalArtManufSupp2 = updateCatalArtManufSupp(catalArtManufSupp);
		CatalArtManufSupp catalArtManufSupp3 = RestAssured.given(REQUEST_SPEC).body(catalArtManufSupp2).put(PATH +"/"+catalArtManufSupp2.getId()).as(CatalArtManufSupp.class);
		assertThat("Updated object CatalArtManufSupp must not have empty id", StringUtils.isNotBlank(catalArtManufSupp3.getId()));
		assertThat("Updated object CatalArtManufSupp must be equals to test object", catalArtManufSupp2.contentEquals(catalArtManufSupp3));
	}
	
	
	@Test
	public void testSearch(){
		CatalArtManufSupp catalArtManufSupp = getCatalArtManufSupp();
		assertThat(catalArtManufSupp, is(notNullValue()));
		assertThat("Object CatalArtManufSupp must have non empty id", StringUtils.isNotBlank(catalArtManufSupp.getId()));
		
		CatalArtManufSuppSearchInput catalArtManufSuppSearchInput = RestAssuredITConfig.createCatalArtManufSuppSearchInput(catalArtManufSupp);
		CatalArtManufSuppSearchResult catalArtManufSuppSearchResult = RestAssured.given(REQUEST_SPEC).body(catalArtManufSuppSearchInput).post(PATH+"/findCustom").as(CatalArtManufSuppSearchResult.class);
		CatalArtManufSupp catalArtManufSupp2 = catalArtManufSuppSearchResult.getResultList().iterator().next();
		assertThat(catalArtManufSupp2, is(notNullValue()));
		assertThat("Return id Objec CatalArtManufSupp is not null", StringUtils.isNotBlank(catalArtManufSupp2.getId()));
		assertThat("Return object CatalArtManufSupp is equals to test object", catalArtManufSupp.contentEquals(catalArtManufSupp2));
		
	} 
	
	
	
	@Test
	public void testList(){
		List<CatalArtManufSupp> catalArtManufSupps = getCatalArtManufSupps();
		assertThat("List Object is not empty", CollectionUtils.isNotEmpty(catalArtManufSupps));
		int size = catalArtManufSupps.size();
		int count=20;
		assertThat(count, equalTo(size));
	}
	
	
	@Test
	public void testDelete(){
		CatalArtManufSupp catalArtManufSupp = getCatalArtManufSupp();
		assertThat(catalArtManufSupp, is(notNullValue()));
		assertThat("Object CatalArtManufSupp must have non empty id", StringUtils.isNotBlank(catalArtManufSupp.getId()));
		Response response = RestAssured.expect().statusCode(HttpStatus.SC_NOT_FOUND).when().given().contentType(ContentType.JSON).delete(PATH+"/"+catalArtManufSupp.getId());
		assertThat(response.statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));
	}
	
	
	
	
	private CatalArtManufSupp createCatalArtManufSupp(){
		 CatalArtManufSupp catalArtManufSupp = new CatalArtManufSupp();
		 catalArtManufSupp.setVatRate(new BigDecimal(19.25));
		 catalArtManufSupp.setPppu(new BigDecimal(2000));
		 catalArtManufSupp.setPppuCurrIso3("XAF");
		 catalArtManufSupp.setMsType(""); 
		 return catalArtManufSupp;
	}
	
	@SuppressWarnings("unchecked")
	private CatalArtManufSupp getCatalArtManufSupp(){
		Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
		CoreAbstIdentifObjectSearchResult<CatalArtManufSupp> response =  RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArtManufSupp> resultList = response.getResultList();
    	CatalArtManufSupp catalArtManufSupp = resultList.get(1);
    	return catalArtManufSupp;
	}
	
	@SuppressWarnings("unchecked")
	private List<CatalArtManufSupp> getCatalArtManufSupps(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalArtManufSupp> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArtManufSupp> resultList = response.getResultList();
    	return resultList;
    }
	
	private CatalArtManufSupp updateCatalArtManufSupp(CatalArtManufSupp catalArtManufSupp){
		catalArtManufSupp.setVatRate(BigDecimal.ZERO);
		catalArtManufSupp.setPppu(new BigDecimal(2500));
		return catalArtManufSupp;
	}

}
