package org.adorsys.adprocmt.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class PrcmtDeliveryLoader extends AbstractObjectLoader<PrcmtDeliveryExcel> {

	@Inject
	private ProcmtDlvryManagerClient dlvryManagerClient;
	
	@Override
	protected PrcmtDeliveryExcel newObject() {
		return new PrcmtDeliveryExcel();
	}

	public PrcmtDeliveryExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public PrcmtDeliveryExcel create(PrcmtDeliveryExcel entity) {
		return null;
	}

	public PrcmtDeliveryExcel update(PrcmtDeliveryExcel found) {
		return null;
	}

	public PrcmtDeliveryExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(PrcmtDeliveryExcel entity, List<PropertyDesc> fields) {
		dlvryManagerClient.saveDelivery(entity);
	}
}
