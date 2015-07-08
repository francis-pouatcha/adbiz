package org.adorsys.adprocmt.loader;

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

@Startup
@Singleton
public class PrcmtLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private PrcmtDeliveryLoader prcmtDeliveryLoader;
	@Inject
	private PrcmtDlvryItemLoader prcmtDlvryItemLoader;
	
	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(PrcmtDeliveryExcel.class.getSimpleName(), prcmtDeliveryLoader);
		dataSheetLoader.registerLoader(PrcmtDlvryItemExcel.class.getSimpleName(), prcmtDlvryItemLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		// Ad least one invetory whent thru.
		List<BaseBatchEvt> found = batchEvtEJB.findByEvtModuleAndEvtKlassAndEvtName(ModConstants.MODULE_NAME, "InvInvtryEvt", CoreHistoryTypeEnum.COMMITTED.name(), 0, 1);
		if(found.isEmpty()) return;

		dataSheetLoader.process();
	}
	
	public void createTemplate(){
	}
	
}
