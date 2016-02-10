package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEndpoint;
import org.adorsys.adcore.xls.CoreAbstLoaderRegistration;
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.jpa.StkArt2Section_;
import org.adorsys.adstock.loader.StkLoaderRegistration;
import org.adorsys.adstock.jpa.StkArt2SectionSearchInput;
import org.adorsys.adstock.jpa.StkArt2SectionSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkart2sections")
public class StkArt2SectionEndpoint extends CoreAbstIdentifiedEndpoint<StkArt2Section> {

	@Inject
	private StkArt2SectionLookup lookup;
	@Inject
	private StkArt2SectionEJB ejb;
	@Inject
	private StkLoaderRegistration loaderRegistration;

	@Override
	protected CoreAbstIdentifLookup<StkArt2Section> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstIdentifiedEJB<StkArt2Section> getEjb() {
		return ejb;
	}

	@Override
	protected Field[] getEntityFields() {
		return StkArt2Section_.class.getFields();
	}

	@Override
	protected CoreAbstIdentifObjectSearchInput<StkArt2Section> newSearchInput() {
		return new StkArt2SectionSearchInput();
	}

	@Override
	protected CoreAbstIdentifObjectSearchResult<StkArt2Section> newSearchResult(
			Long count, Long total, List<StkArt2Section> resultList,
			CoreAbstIdentifObjectSearchInput<StkArt2Section> searchInput) {
		return new StkArt2SectionSearchResult(count, total, resultList, searchInput);
	}
	
	@Override
	protected CoreAbstLoaderRegistration getLoaderRegistration() {
		return loaderRegistration;
	}
	
}