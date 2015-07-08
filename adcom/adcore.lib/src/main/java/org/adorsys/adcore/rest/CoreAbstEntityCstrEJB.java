package org.adorsys.adcore.rest;

import org.adorsys.adcore.jpa.CoreAbstEntityCstr;

public abstract class CoreAbstEntityCstrEJB<C extends CoreAbstEntityCstr>
		extends CoreAbstIdentifiedEJB<C> {

	public abstract C newCsrInstance();
}
