package org.adorsys.adprocmt.trigger;

import java.util.HashMap;

import javax.ejb.Stateless;
/**
 * 
 * @author guymoyo
 *
 */
@Stateless
public class TriggerModeHandlerFactory {


	/** The restriction handler mapping. */
	private HashMap<String, TriggerModeExecuter> triggerModeHandlerMapping = new HashMap<String, TriggerModeExecuter>();

	/**
	 * Register handler.
	 * 
	 * @param key
	 *            the key
	 * @param restrictionHandler
	 *            the restriction handler
	 */
	public void registerHandler(String key ,TriggerModeExecuter restrictionHandler){
		triggerModeHandlerMapping.put(key, restrictionHandler);
	}

	/**
	 * Un register handler.
	 * 
	 * @param key
	 *            the key
	 */
	public void unRegisterHandler(String key){
		if(triggerModeHandlerMapping.containsKey(key))
			triggerModeHandlerMapping.remove(key);
	}

	/**
	 * Find handler.
	 * 
	 * @param key
	 *            the key
	 * @return the user restriction checker
	 */
	public TriggerModeExecuter findHandler(String key ){
		return triggerModeHandlerMapping.get(key);
	}
	
	
}
