package org.adorsys.adprocmt.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class PrcmtDlvryItemLoader extends AbstractObjectLoader<PrcmtDlvryItemExcel> {

	@Inject
	private ProcmtDlvryManagerClient dlvryManagerClient;

	@Override
	protected PrcmtDlvryItemExcel newObject() {
		return new PrcmtDlvryItemExcel();
	}

	public PrcmtDlvryItemExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public PrcmtDlvryItemExcel create(PrcmtDlvryItemExcel entity) {
		return null;
	}

	public PrcmtDlvryItemExcel update(PrcmtDlvryItemExcel found) {
		return null;
	}

	public PrcmtDlvryItemExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(PrcmtDlvryItemExcel entity, List<PropertyDesc> fields) {
		dlvryManagerClient.saveDlvryItem(entity);
	}

	@Override
	protected void done() {
		//dlvryManagerClient.done();
		dlvryManagerClient.update();
	}
	
	
}
