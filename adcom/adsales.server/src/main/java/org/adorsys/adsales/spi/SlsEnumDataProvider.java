package org.adorsys.adsales.spi;

import java.util.List;
import java.util.Map;

public interface SlsEnumDataProvider {
	public Map<String, Map<String, List<String>>> getHistoryTypeData();
	public Map<String, Map<String, List<String>>> getInvoiceTypeData();
	public Map<String, Map<String, List<String>>> getInvceStatusData();
	public Map<String, Map<String, List<String>>> getProcStepData();
	public Map<String, Map<String, List<String>>> getRoleInSalesData();
	public Map<String, Map<String, List<String>>> getSalesStatusData();
	public Map<String, Map<String, List<String>>> getSttlmtOpData();
}
