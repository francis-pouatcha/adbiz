package org.adorsys.adbase.spi;

import java.util.List;
import java.util.Map;

public interface BaseEnumDataProvider {
	public Map<String, Map<String, List<String>>> getDocTypeData();
	public Map<String, Map<String, List<String>>> getHistoryTypeData();
	public Map<String, Map<String, List<String>>> getProcessStatusData();
	public Map<String, Map<String, List<String>>> getProcStepData();
	public Map<String, Map<String, List<String>>> getRoleInProcessData();
	public Map<String, Map<String, List<String>>> getSttlmtOpData();
}
