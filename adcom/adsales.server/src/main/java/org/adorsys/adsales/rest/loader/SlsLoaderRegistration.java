package org.adorsys.adsales.rest.loader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adstock.api.ModConstants;
import org.apache.commons.lang3.time.DateUtils;

@Startup
@Singleton
public class SlsLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private SlsSalesOrderLoader salesOrderLoader;
	@Inject
	private SlsSOItemLoader soItemLoader;
	
	@Inject
	private SlsSalesOrderManagerClient salesOrderManagerClient;

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(SlsSalesOrderExcel.class.getSimpleName(), salesOrderLoader);
		dataSheetLoader.registerLoader(SlsSOItemExcel.class.getSimpleName(), soItemLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="1/37" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		// At least one delivery has happened.
		List<BaseBatchEvt> found = batchEvtEJB.findByEvtModuleAndEvtKlassAndEvtName(ModConstants.MODULE_NAME, "PrcmtDeliveryEvt", CoreHistoryTypeEnum.COMMITTED.name(), 0, 1);
		if(found.isEmpty()) return;
		dataSheetLoader.process();
	}

	@Schedule(minute = "*", hour="*", persistent=false, second="*/33")
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void doSale() throws Exception {
		salesOrderManagerClient.doSale();
	}
	
	public void createTemplate(){
	}
	
}
