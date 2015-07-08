package org.adorsys.adinvtry.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class InvInvtryLoader extends AbstractObjectLoader<InvInvtryExcel> {

	@Inject
	private InvInvtryManagerClient dlvryManagerClient;
	
	@Override
	protected InvInvtryExcel newObject() {
		return new InvInvtryExcel();
	}

	public InvInvtryExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public InvInvtryExcel create(InvInvtryExcel entity) {
		return null;
	}

	public InvInvtryExcel update(InvInvtryExcel found) {
		return null;
	}

	public InvInvtryExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(InvInvtryExcel entity, List<PropertyDesc> fields) {
		dlvryManagerClient.saveInvtry(entity);
	}
}
