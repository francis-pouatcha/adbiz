package org.adorsys.adsales.rest.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class SlsSOItemLoader extends AbstractObjectLoader<SlsSOItemExcel> {

	@Inject
	private SlsSalesOrderManagerClient salesOrderManagerClient;

	@Override
	protected SlsSOItemExcel newObject() {
		return new SlsSOItemExcel();
	}

	public SlsSOItemExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public SlsSOItemExcel create(SlsSOItemExcel entity) {
		return null;
	}

	public SlsSOItemExcel update(SlsSOItemExcel found) {
		return null;
	}

	public SlsSOItemExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(SlsSOItemExcel entity, List<PropertyDesc> fields) {
		salesOrderManagerClient.saveSalesOrderItem(entity);
	}

	@Override
	protected void done() {
		salesOrderManagerClient.done();
	}
	
	
}
