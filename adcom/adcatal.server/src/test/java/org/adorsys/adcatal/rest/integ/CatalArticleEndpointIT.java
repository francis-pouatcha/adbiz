package org.adorsys.adcatal.rest.integ;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalArticleSearchResult;
import org.adorsys.adcatal.rest.integ.utils.RestAssuredITConfig;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

public class CatalArticleEndpointIT extends RestAssuredITConfig {

	private static final String PATH = "/catalarticles";
	private static final ResponseSpecification RESPONSE_SPEC = new ResponseSpecBuilder().expectStatusCode(200).build();
	private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	
	@Test
	public void testCreate() {
		CatalArticle catalArticle = createCatalArticle();
		assertThat("New Object must have empty id", StringUtils.isBlank(catalArticle.getId()));
		CatalArticle catalArticle2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArticle).post(PATH).as(CatalArticle.class);
		assertThat(catalArticle2, is(notNullValue()));
		assertThat("Created Object must have non empty id", StringUtils.isNotBlank(catalArticle2.getId()));
		assertThat("Created Object must be equals", catalArticle.contentEquals(catalArticle2));
		
	}
	
	
	@Test
	public void testUpdate(){
		CatalArticle catalArticle = getCatalArticle();
		assertThat(catalArticle, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalArticle.getId()));
		CatalArticle catalArticle2 = updateCatalArticle(catalArticle);
		CatalArticle catalArticle3 = RestAssured.given(REQUEST_SPEC).body(catalArticle2).put(PATH +"/"+catalArticle2.getId()).as(CatalArticle.class);
		assertThat("Updated object must not have empty id", StringUtils.isNotBlank(catalArticle3.getId()));
		assertThat("Updated object must be equals to test object", catalArticle2.contentEquals(catalArticle3));
	}
	
	
	@Test
	public void testList(){
		List<CatalArticle> catalArticles = getCatalArticles();
		assertThat("List Object is not empty", CollectionUtils.isNotEmpty(catalArticles));
		int size = catalArticles.size();
		int count=20;
		assertThat(count, equalTo(size));
	}
	
	
	@Test
	public void testDesactivate(){
		CatalArticle catalArticle = getCatalArticle();
		assertThat(catalArticle, notNullValue());
		assertThat("Get Object must not have empty id", StringUtils.isNotBlank(catalArticle.getId()));
		CatalArticle catalArticle2 = desactiveCatalArticle(catalArticle);
		CatalArticle catalArticle3 = RestAssured.given(REQUEST_SPEC).body(catalArticle2).put(PATH +"/"+catalArticle2.getId()).as(CatalArticle.class); 
	    assertThat(Boolean.TRUE, equalTo(catalArticle3.getActive()));
	}
	
	
	@Test
	public void testPrintList(){
		List<CatalArticle> catalArticles = getCatalArticles();
		
	}
	
	
	@Test
	public void testSearch(){
		
		CatalArticle catalArticle = getCatalArticle();
		assertThat(catalArticle, is(notNullValue()));
		
		CatalArticleSearchInput catalArticleSearchInput = RestAssuredITConfig.createCatalArticleSearchInput(catalArticle);
		CatalArticleSearchResult catalArticleSearchResult = RestAssured.given(REQUEST_SPEC).body(catalArticleSearchInput).post(PATH+"/findCustom").as(CatalArticleSearchResult.class);
		CatalArticle catalArticle2 = catalArticleSearchResult.getResultList().iterator().next();
		assertThat(catalArticle2, is(notNullValue()));
		assertThat("Return id Object is not null", StringUtils.isNotBlank(catalArticle2.getId()));
		assertThat("Return object is equals to test object", catalArticle.contentEquals(catalArticle2));
		
	}
	
	
	
	private CatalArticle createCatalArticle(){
		CatalArticle catalArticle = new CatalArticle();
		catalArticle.setIdentif(RandomStringUtils.randomAlphanumeric(7).toUpperCase());
		catalArticle.setActive(true);
		catalArticle.setAuthorizedSale(true);
		catalArticle.setSppu(new BigDecimal(RandomStringUtils.randomNumeric(4)));
		catalArticle.setSppuCurrIso3("XAF");
		catalArticle.setMaxDisctRate(new BigDecimal(RandomStringUtils.randomNumeric(1)));
		catalArticle.setMaxStockQty(new BigDecimal(RandomStringUtils.randomNumeric(4)));
		catalArticle.setMinStockQty(new BigDecimal(RandomStringUtils.randomNumeric(2)));
		catalArticle.setVatRate(new BigDecimal("19.25"));
		// Defaults to FiFo
		catalArticle.setLotMgtScheme("FiFo");
		catalArticle.setMngByLot(true);		
		return catalArticle;
	}
	
	
	
	private CatalArticle updateCatalArticle(CatalArticle catalArticle){
		catalArticle.setActive(false);
		catalArticle.setAuthorizedSale(false);
		catalArticle.setVatRate(new BigDecimal(0));
		return catalArticle;
	}
	
	private CatalArticle desactiveCatalArticle(CatalArticle catalArticle){
		catalArticle.setActive(false);
		return catalArticle;
	}
	
    @SuppressWarnings("unchecked")
	private CatalArticle getCatalArticle(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalArticle> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArticle> resultList = response.getResultList();
    	CatalArticle catalArticle = resultList.get(1);
    	return catalArticle;
    }
    
    @SuppressWarnings("unchecked")
	private List<CatalArticle> getCatalArticles(){
    	Map<String, Integer> params = RestAssuredITConfig.getRequestsParams();
    	CoreAbstIdentifObjectSearchResult<CatalArticle> response = RestAssured.expect().spec(RESPONSE_SPEC).given().parameters(params).when().get(PATH).as(CoreAbstIdentifObjectSearchResult.class);
    	List<CatalArticle> resultList = response.getResultList();
    	return resultList;
    }
}
