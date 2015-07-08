package org.adorsys.adinvtry.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class InvInvtryItemLoader extends AbstractObjectLoader<InvInvtryItemExcel> {

	@Inject
	private InvInvtryManagerClient dlvryManagerClient;

	@Override
	protected InvInvtryItemExcel newObject() {
		return new InvInvtryItemExcel();
	}

	public InvInvtryItemExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public InvInvtryItemExcel create(InvInvtryItemExcel entity) {
		return null;
	}

	public InvInvtryItemExcel update(InvInvtryItemExcel found) {
		return null;
	}

	public InvInvtryItemExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(InvInvtryItemExcel entity, List<PropertyDesc> fields) {
		dlvryManagerClient.saveInvtryItem(entity);
	}

	@Override
	protected void done() {
		dlvryManagerClient.done();
	}
	
	
}
