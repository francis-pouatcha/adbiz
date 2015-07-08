package org.adorsys.adsales.rest.loader;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adcore.xls.PropertyDesc;

@Stateless
public class SlsSalesOrderLoader extends AbstractObjectLoader<SlsSalesOrderExcel> {

	@Inject
	private SlsSalesOrderManagerClient salesOrderManagerClient;
	
	@Override
	protected SlsSalesOrderExcel newObject() {
		return new SlsSalesOrderExcel();
	}

	public SlsSalesOrderExcel findByIdentif(String identif, Date validOn) {
		return null;
	}

	public SlsSalesOrderExcel create(SlsSalesOrderExcel entity) {
		return null;
	}

	public SlsSalesOrderExcel update(SlsSalesOrderExcel found) {
		return null;
	}

	public SlsSalesOrderExcel deleteById(String id) {
		return null;
	}

	@Override
	protected void save(SlsSalesOrderExcel entity, List<PropertyDesc> fields) {
		salesOrderManagerClient.saveSalesOrder(entity);
	}
}
