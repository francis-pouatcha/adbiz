package org.adorsys.adprocmt.spi;

import java.util.List;
import java.util.Map;

public interface ProcmtEnumDataProvider {
	public Map<String, Map<String, List<String>>> getProcmtPOTriggerModeData();
	public Map<String, Map<String, List<String>>> getProcmtPOTypeData();
}
