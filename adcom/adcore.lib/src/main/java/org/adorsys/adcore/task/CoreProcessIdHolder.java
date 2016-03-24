package org.adorsys.adcore.task;

import java.util.UUID;

import javax.ejb.Singleton;

/**
 * This is a singleton bean that holds the processId of the web application
 * instance running a task.
 * 
 * @author fpo
 *
 */
@Singleton
public class CoreProcessIdHolder {

	private final String processId = UUID.randomUUID().toString();
	
	public String getProcessId(){
		return processId;
	}
}
