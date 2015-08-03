package org.adorsys.adcatal.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.rest.CatalArticleEJB;
import org.adorsys.adcatal.rest.CatalArticleLookup;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;

@Stateless
public class CatalArticleLoader extends CoreAbstEntityLoader<CatalArticle> {

	@Inject
	private CatalArticleEJB ejb;
	@Inject
	private CatalArticleLookup lookup;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB 
	private CatalArticleLoader loader;	

	@Override
	protected CoreAbstIdentifLookup<CatalArticle> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArticle> getEjb() {
		return ejb;
	}

	@Override
	protected CatalArticle newObject() {
		return new CatalArticle();
	}
	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	protected CoreAbstLoader<CatalArticle> getLoader() {
		return loader;
	}
}
