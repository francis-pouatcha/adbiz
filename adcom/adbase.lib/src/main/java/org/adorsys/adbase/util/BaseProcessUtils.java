package org.adorsys.adbase.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.jgroups.util.UUID;

@Singleton
public class BaseProcessUtils {

	private String processId;
	
	@PostConstruct
	private void postConstruct(){
		processId = UUID.randomUUID().toString();
	}

	public String getProcessId() {
		return processId;
	}
}
