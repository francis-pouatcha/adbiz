package org.adorsys.adcore.rest;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;

public abstract class CoreAbstSpleBsnsItemEJB<I extends CoreAbstBsnsItem> extends CoreAbstIdentifiedEJB<I>{

	protected abstract CoreAbstBsnsItemRepo<I> getBsnsRepo();
	
	protected CoreAbstIdentifDataRepo<I> getRepo(){
		return getBsnsRepo();
	};

}

