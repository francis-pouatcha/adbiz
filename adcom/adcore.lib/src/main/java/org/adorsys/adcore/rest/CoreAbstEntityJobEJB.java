package org.adorsys.adcore.rest;

import org.adorsys.adcore.jpa.CoreAbstEntityJob;

public abstract class CoreAbstEntityJobEJB<J extends CoreAbstEntityJob> extends CoreAbstIdentifiedEJB<J>{

	public abstract J newJobInstance();
}
