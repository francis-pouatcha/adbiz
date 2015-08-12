package org.adorsys.adcore.task;

import java.util.HashMap;
import java.util.Map;

import org.adorsys.adcore.jpa.CoreAbstPrcssngStep;


/**
 * Subclass of this monitor muss be singleton in the scope of their application.
 * 
 * @author francis
 *
 */
//@Singleton
public abstract class CoreAbstPrcssgStepMonitor {
	
	/*
	 * Map of steps being processed by this context.
	 */
	public Map<String, CoreAbstPrcssngStep> prcssngStep = new HashMap<String, CoreAbstPrcssngStep>();
}
