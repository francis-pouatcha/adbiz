package org.adorsys.adcore.rest;

import org.adorsys.adcore.jpa.CoreAbstEntityStep;

public abstract class CoreAbstEntityStepEJB<S extends CoreAbstEntityStep> extends CoreAbstIdentifiedEJB<S>{
	public abstract S newStepInstance();
}
