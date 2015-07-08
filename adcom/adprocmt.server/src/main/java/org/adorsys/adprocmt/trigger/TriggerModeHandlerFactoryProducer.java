package org.adorsys.adprocmt.trigger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
/**
 * 
 * @author guymoyo
 *
 */
@Singleton
public class TriggerModeHandlerFactoryProducer {
 
	@Inject
	private TriggerModeHandlerFactory triggerModeHandlerFactory;
	@Inject
	private ManualTriggerModeHandler manualTriggerModeHandler;
	@Inject
	private MostSoldTriggerModeHandler mostSoldTriggerModeHandler;
	@Inject
	private StockShortageTriggerModeHandler stockShortageTriggerModeHandler;
	
	@PostConstruct
	public void prostCOnstruct(){
         
		triggerModeHandlerFactory.registerHandler(ManualTriggerModeHandler.getKey(), manualTriggerModeHandler);
		triggerModeHandlerFactory.registerHandler(MostSoldTriggerModeHandler.getKey(), mostSoldTriggerModeHandler);
		triggerModeHandlerFactory.registerHandler(StockShortageTriggerModeHandler.getKey(), stockShortageTriggerModeHandler);
		
	}

	/**
	 * Gets the restriction handler factory.
	 * 
	 * @return the restriction handler factory
	 */
	public TriggerModeHandlerFactory getTriggerModeHandlerFactory() {
		return triggerModeHandlerFactory;
	}

	/**
	 * Sets the restriction handler factory.
	 * 
	 * @param restrictionHandlerFactory
	 *            the new restriction handler factory
	 */
	public void setTriggerModeHandlerFactory(
			TriggerModeHandlerFactory triggerModeHandlerFactory) {
		this.triggerModeHandlerFactory = triggerModeHandlerFactory;
	}
}
