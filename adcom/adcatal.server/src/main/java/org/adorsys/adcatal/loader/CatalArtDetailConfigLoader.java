package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.rest.CatalArtDetailConfigEJB;
import org.adorsys.adcatal.rest.CatalArtDetailConfigLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;

@Stateless
public class CatalArtDetailConfigLoader extends
		CoreAbstEntityLoader<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;
	@Inject
	private CatalArtDetailConfigLookup lookup;

	@Override
	protected CoreAbstIdentifLookup<CatalArtDetailConfig> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<CatalArtDetailConfig> getEjb() {
		return ejb;
	}

	@Override
	protected CatalArtDetailConfig newObject() {
		return new CatalArtDetailConfig();
	}
}
