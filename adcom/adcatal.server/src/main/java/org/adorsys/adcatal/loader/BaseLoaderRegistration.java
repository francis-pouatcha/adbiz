package org.adorsys.adcatal.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.jpa.CatalProdFmlyLangMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Startup
@Singleton
public class BaseLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private CatalArtDetailConfigLoader catalArtDetailConfigLoader;
	@Inject
	private CatalArtEquivalenceLoader catalArtEquivalenceLoader;
	@Inject
	private CatalArticleLoader catalArticleLoader;
	@Inject
	private CatalArtLangMappingLoader catalArtLangMappingLoader;
	@Inject
	private CatalArtManufSuppLoader catalArtManufSuppLoader;
	@Inject
	private CatalPicMappingLoader catalPicMappingLoader;
	@Inject
	private CatalProdFmlyLoader catalProductFamilyLoader;
	@Inject
	private CatalProdFmlyLangMapLoader catalProdFmlyLangMapLoader;
	@Inject
	private CatalArt2ProductFamilyLoader catalArt2ProductFamilyLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(CatalArtDetailConfig.class.getSimpleName(), catalArtDetailConfigLoader);
		dataSheetLoader.registerLoader(CatalArtEquivalence.class.getSimpleName(), catalArtEquivalenceLoader);
		dataSheetLoader.registerLoader(CatalArticle.class.getSimpleName(), catalArticleLoader);
		dataSheetLoader.registerLoader(CatalArtLangMapping.class.getSimpleName(), catalArtLangMappingLoader);
		dataSheetLoader.registerLoader(CatalArtManufSupp.class.getSimpleName(), catalArtManufSuppLoader);
		dataSheetLoader.registerLoader(CatalPicMapping.class.getSimpleName(), catalPicMappingLoader);
		dataSheetLoader.registerLoader(CatalProdFmly.class.getSimpleName(), catalProductFamilyLoader);
		dataSheetLoader.registerLoader(CatalProdFmlyLangMap.class.getSimpleName(), catalProdFmlyLangMapLoader);
		dataSheetLoader.registerLoader(CatalArt2ProductFamily.class.getSimpleName(), catalArt2ProductFamilyLoader);

		createTemplate();
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		dataSheetLoader.process();
	}
	
	public void createTemplate(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		catalProductFamilyLoader.createTemplate(workbook);
		catalProdFmlyLangMapLoader.createTemplate(workbook);
		catalArticleLoader.createTemplate(workbook);
		catalArtLangMappingLoader.createTemplate(workbook);
		catalArt2ProductFamilyLoader.createTemplate(workbook);
		catalPicMappingLoader.createTemplate(workbook);
		catalArtManufSuppLoader.createTemplate(workbook);
		catalArtEquivalenceLoader.createTemplate(workbook);
		catalArtDetailConfigLoader.createTemplate(workbook);
		dataSheetLoader.exportTemplate(workbook);
	}
	
}
