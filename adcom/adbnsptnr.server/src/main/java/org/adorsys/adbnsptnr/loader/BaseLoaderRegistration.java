package org.adorsys.adbnsptnr.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.jpa.BpPtnrContract;
import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.jpa.BpPtnrIdDtls;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Startup
@Singleton
public class BaseLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private BpPtnrCtgryLoader bpPtnrCtgryLoader;
	@Inject
	private BpPtnrCtgryDtlsLoader bpPtnrCtgryDtlsLoader;
	@Inject
	private BpPtnrCreditDtlsLoader bpPtnrCreditDtlsLoader;
	@Inject
	private BpPtnrContactLoader bpPtnrContactLoader;
	@Inject
	private BpLegalPtnrIdLoader bpLegalPtnrIdLoader;
	@Inject
	private BpInsurranceLoader bpInsurranceLoader;
	@Inject
	private BpIndivPtnrNameLoader bpIndivPtnrNameLoader;
	@Inject
	private BpCtgryOfPtnrLoader bpCtgryOfPtnrLoader;
	@Inject
	private BpCtgryDscntLoader bpCtgryDscntLoader;
	@Inject
	private BpBnsPtnrLoader bpBnsPtnrLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(BpPtnrCtgry.class.getSimpleName(), bpPtnrCtgryLoader);
		dataSheetLoader.registerLoader(BpPtnrCtgryDtls.class.getSimpleName(), bpPtnrCtgryDtlsLoader);
		dataSheetLoader.registerLoader(BpPtnrContract.class.getSimpleName(), bpCtgryDscntLoader);
		dataSheetLoader.registerLoader(BpBnsPtnr.class.getSimpleName(), bpBnsPtnrLoader);
		dataSheetLoader.registerLoader(BpLegalPtnrId.class.getSimpleName(), bpLegalPtnrIdLoader);
		dataSheetLoader.registerLoader(BpIndivPtnrName.class.getSimpleName(), bpIndivPtnrNameLoader);
		dataSheetLoader.registerLoader(BpPtnrContact.class.getSimpleName(), bpPtnrContactLoader);
		dataSheetLoader.registerLoader(BpCtgryOfPtnr.class.getSimpleName(), bpCtgryOfPtnrLoader);
		dataSheetLoader.registerLoader(BpPtnrCreditDtls.class.getSimpleName(), bpPtnrCreditDtlsLoader);
		dataSheetLoader.registerLoader(BpInsurrance.class.getSimpleName(), bpInsurranceLoader);
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
		bpPtnrCtgryLoader.createTemplate(workbook);
		bpPtnrCtgryDtlsLoader.createTemplate(workbook);
		bpCtgryDscntLoader.createTemplate(workbook);
		bpBnsPtnrLoader.createTemplate(workbook);
		bpLegalPtnrIdLoader.createTemplate(workbook);
		bpIndivPtnrNameLoader.createTemplate(workbook);
		bpPtnrContactLoader.createTemplate(workbook);
		bpCtgryOfPtnrLoader.createTemplate(workbook);
		bpPtnrCreditDtlsLoader.createTemplate(workbook);
		bpInsurranceLoader.createTemplate(workbook);
		dataSheetLoader.exportTemplate(workbook);
	}
	
}
