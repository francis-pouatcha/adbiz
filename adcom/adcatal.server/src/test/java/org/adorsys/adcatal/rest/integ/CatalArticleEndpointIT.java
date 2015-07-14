package org.adorsys.adcatal.rest.integ;

import java.math.BigDecimal;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalArticleSearchResult;
import org.adorsys.adcatal.rest.integ.utils.RestAssuredITConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

public class CatalArticleEndpointIT extends RestAssuredITConfig {

	private static final String PATH = "/catalarticles";
	@Test
	public void testCreateFind() {
		CatalArticle catalArticle = createCatalArticle();
		Assert.assertTrue("New Object must have empty id", org.apache.commons.lang3.StringUtils.isBlank(catalArticle.getId()));
		CatalArticle catalArticle2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArticle).post(PATH).as(CatalArticle.class);
		Assert.assertNotNull(catalArticle2);
		Assert.assertTrue("Created Object must have non empty id", org.apache.commons.lang3.StringUtils.isNotBlank(catalArticle2.getId()));
		Assert.assertTrue("Created Object must be equals", catalArticle.contentEquals(catalArticle2));
		
		String identif = catalArticle2.getIdentif();
		
		CatalArticle catalArticle3 = RestAssured.get(PATH + "/"+ identif).as(CatalArticle.class);
		Assert.assertTrue("Created Object must be equals", catalArticle.contentEquals(catalArticle3));	
	}

	@Test
	public void testCreateFindByIdentifFromTo() {
		CatalArticle catalArticle = createCatalArticle();
		Assert.assertTrue("New Object must have empty id", org.apache.commons.lang3.StringUtils.isBlank(catalArticle.getId()));
		CatalArticle catalArticle2 = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArticle).post(PATH).as(CatalArticle.class);
		Assert.assertNotNull(catalArticle2);
		Assert.assertTrue("Created Object must have non empty id", org.apache.commons.lang3.StringUtils.isNotBlank(catalArticle2.getId()));
		Assert.assertTrue("Created Object must be equals", catalArticle.contentEquals(catalArticle2));
		
		String identif = catalArticle2.getIdentif();
		
		CatalArticleSearchInput catalArticleSearchInput = new CatalArticleSearchInput();
		catalArticleSearchInput.setIdentifFrom(identif);
		catalArticleSearchInput.setIdentifTo(identif);
		CatalArticleSearchResult catalArticleSearchResult = RestAssured.expect().statusCode(200).when().given().contentType(ContentType.JSON).body(catalArticleSearchInput).post(PATH + "/findCustom").as(CatalArticleSearchResult.class);
		Assert.assertNotNull(catalArticleSearchResult);
		Assert.assertTrue("Expecting one result", catalArticleSearchResult.getCount()==1);
		CatalArticle catalArticle3 = catalArticleSearchResult.getResultList().iterator().next();
		Assert.assertTrue("Created Object must be equals", catalArticle.contentEquals(catalArticle3));
		
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
}
