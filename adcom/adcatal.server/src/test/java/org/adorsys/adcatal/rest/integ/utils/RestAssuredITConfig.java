package org.adorsys.adcatal.rest.integ.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamilySearchInput;
import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtLangMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMapSearchInput;
import org.adorsys.adcatal.jpa.CatalProdFmlySearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.specification.ResponseSpecification;

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
	
	public static Map<String, Integer> getRequestsParams(){
		CatalArticleSearchInput catalArticleSearchInput = new CatalArticleSearchInput();
		catalArticleSearchInput.setStart(0);
		catalArticleSearchInput.setMax(20);
		Map<String, Integer> parameters = new HashMap<String, Integer>();
		parameters.put("start", catalArticleSearchInput.getStart());
		parameters.put("max", catalArticleSearchInput.getMax());
	    return parameters;
	}
	
	
	public static CatalArticleSearchInput createCatalArticleSearchInput(CatalArticle catalArticle){
		
		CatalArticleSearchInput catalArticleSearchInput = new CatalArticleSearchInput();
		catalArticleSearchInput.setIdentifFrom(catalArticle.getIdentif());
		catalArticleSearchInput.setIdentifTo(catalArticle.getIdentif());
		catalArticleSearchInput.setValueDtFrom(DateUtil.format(catalArticle.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArticleSearchInput.setValueDtTo(DateUtil.format(catalArticle.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArticleSearchInput.setClassName(CatalArticleSearchInput.class.getName());
		catalArticleSearchInput.setStart(0);
		catalArticleSearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("active", "authorizedSale", "sppuCurrIso3"));
		catalArticleSearchInput.setFieldNames(fieldNames);
		catalArticleSearchInput.setEntity(catalArticle);
		return catalArticleSearchInput;
	}
	
	
	public static CatalArtLangMappingSearchInput createCatalArtLangMappingSearchInput(CatalArtLangMapping catalArtLangMapping){
		
		CatalArtLangMappingSearchInput catalArtLangMappingSearchInput = new CatalArtLangMappingSearchInput();
		catalArtLangMappingSearchInput.setIdentifFrom(catalArtLangMapping.getIdentif());
		catalArtLangMappingSearchInput.setIdentifTo(catalArtLangMapping.getIdentif());
		catalArtLangMappingSearchInput.setValueDtFrom(DateUtil.format(catalArtLangMapping.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArtLangMappingSearchInput.setValueDtTo(DateUtil.format(catalArtLangMapping.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArtLangMappingSearchInput.setClassName(CatalArtLangMappingSearchInput.class.getName());
		catalArtLangMappingSearchInput.setStart(0);
		catalArtLangMappingSearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("artName", "shortName", "substances"));
		catalArtLangMappingSearchInput.setFieldNames(fieldNames);
		catalArtLangMappingSearchInput.setEntity(catalArtLangMapping);
		return catalArtLangMappingSearchInput;
	}
	
	
	public static CatalArt2ProductFamilySearchInput createArt2ProductFamilySearchInput(CatalArt2ProductFamily catalArt2ProductFamily){
		
		CatalArt2ProductFamilySearchInput catalArt2ProductFamilySearchInput = new CatalArt2ProductFamilySearchInput();
		catalArt2ProductFamilySearchInput.setIdentifFrom(catalArt2ProductFamily.getIdentif());
		catalArt2ProductFamilySearchInput.setIdentifTo(catalArt2ProductFamily.getIdentif());
		catalArt2ProductFamilySearchInput.setValueDtFrom(DateUtil.format(catalArt2ProductFamily.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArt2ProductFamilySearchInput.setValueDtTo(DateUtil.format(catalArt2ProductFamily.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArt2ProductFamilySearchInput.setClassName(CatalArt2ProductFamilySearchInput.class.getName());
		catalArt2ProductFamilySearchInput.setStart(0);
		catalArt2ProductFamilySearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("artPic", "famCode"));
		catalArt2ProductFamilySearchInput.setFieldNames(fieldNames);
		catalArt2ProductFamilySearchInput.setEntity(catalArt2ProductFamily);
		return catalArt2ProductFamilySearchInput;
	}
	
	
	public static CatalPicMappingSearchInput createCatalPicMappingSearchInput(CatalPicMapping catalPicMapping){
		
		CatalPicMappingSearchInput catalPicMappingSearchInput = new CatalPicMappingSearchInput();
		catalPicMappingSearchInput.setIdentifFrom(catalPicMapping.getIdentif());
		catalPicMappingSearchInput.setIdentifTo(catalPicMapping.getIdentif());
		catalPicMappingSearchInput.setValueDtFrom(DateUtil.format(catalPicMapping.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalPicMappingSearchInput.setValueDtTo(DateUtil.format(catalPicMapping.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalPicMappingSearchInput.setClassName(CatalPicMappingSearchInput.class.getName());
		catalPicMappingSearchInput.setStart(0);
		catalPicMappingSearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("artIdentif", "code", "priority"));
		catalPicMappingSearchInput.setFieldNames(fieldNames);
		catalPicMappingSearchInput.setEntity(catalPicMapping);
		return catalPicMappingSearchInput;
	}
	
	
	public static CatalProdFmlySearchInput createCatalProdFmlySearchInput(CatalProdFmly catalProdFmly){
		
		CatalProdFmlySearchInput catalProdFmlySearchInput = new CatalProdFmlySearchInput();
		catalProdFmlySearchInput.setIdentifFrom(catalProdFmly.getIdentif());
		catalProdFmlySearchInput.setIdentifTo(catalProdFmly.getIdentif());
		catalProdFmlySearchInput.setValueDtFrom(DateUtil.format(catalProdFmly.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalProdFmlySearchInput.setValueDtTo(DateUtil.format(catalProdFmly.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalProdFmlySearchInput.setClassName(CatalProdFmlySearchInput.class.getName());
		catalProdFmlySearchInput.setStart(0);
		catalProdFmlySearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("parentIdentif", "famPath"));
		catalProdFmlySearchInput.setFieldNames(fieldNames);
		catalProdFmlySearchInput.setEntity(catalProdFmly);
		return 	catalProdFmlySearchInput;
		
	}
	
	public static CatalProdFmlyLangMapSearchInput createCatalProdFmlyLangMapSearchInput(CatalProdFmlyLangMap catalProdFmlyLangMap){
		
		CatalProdFmlyLangMapSearchInput catalProdFmlyLangMapSearchInput = new CatalProdFmlyLangMapSearchInput();
		catalProdFmlyLangMapSearchInput.setIdentifFrom(catalProdFmlyLangMap.getIdentif()); 
		catalProdFmlyLangMapSearchInput.setIdentifTo(catalProdFmlyLangMap.getIdentif());
		catalProdFmlyLangMapSearchInput.setValueDtFrom(DateUtil.format(catalProdFmlyLangMap.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalProdFmlyLangMapSearchInput.setValueDtTo(DateUtil.format(catalProdFmlyLangMap.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalProdFmlyLangMapSearchInput.setClassName(CatalProdFmlyLangMapSearchInput.class.getName());
		catalProdFmlyLangMapSearchInput.setStart(0);
		catalProdFmlyLangMapSearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("famPath"));
		catalProdFmlyLangMapSearchInput.setFieldNames(fieldNames);
		catalProdFmlyLangMapSearchInput.setEntity(catalProdFmlyLangMap);
		return 	catalProdFmlyLangMapSearchInput;
		
	}
	
	
	public static CatalArtManufSuppSearchInput createCatalArtManufSuppSearchInput(CatalArtManufSupp catalArtManufSupp){
		
		CatalArtManufSuppSearchInput catalArtManufSuppSearchInput = new CatalArtManufSuppSearchInput();
		catalArtManufSuppSearchInput.setIdentifFrom(catalArtManufSupp.getIdentif()); 
		catalArtManufSuppSearchInput.setIdentifTo(catalArtManufSupp.getIdentif());
		catalArtManufSuppSearchInput.setValueDtFrom(DateUtil.format(catalArtManufSupp.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArtManufSuppSearchInput.setValueDtTo(DateUtil.format(catalArtManufSupp.getValueDt(), DateUtil.DATE_TIME_FORMAT));
		catalArtManufSuppSearchInput.setClassName(CatalArtManufSuppSearchInput.class.getName());
		catalArtManufSuppSearchInput.setStart(0);
		catalArtManufSuppSearchInput.setMax(5);
		List<String> fieldNames= new ArrayList<String>();
		fieldNames.addAll(Arrays.asList("famPath"));
		catalArtManufSuppSearchInput.setFieldNames(fieldNames);
		catalArtManufSuppSearchInput.setEntity(catalArtManufSupp);
		return 	catalArtManufSuppSearchInput;
	}
	
	
	
	
	
	
	
}
