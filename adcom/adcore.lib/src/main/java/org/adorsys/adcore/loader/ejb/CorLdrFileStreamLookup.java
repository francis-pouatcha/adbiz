package org.adorsys.adcore.loader.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.adorsys.adcore.loader.repo.CorLdrFileStreamRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CorLdrFileStreamLookup extends
		CoreAbstIdentifLookup<CorLdrFileStream> {

	@Inject
	private CorLdrFileStreamRepo repo;

	@Override
	protected Class<CorLdrFileStream> getEntityClass() {
		return CorLdrFileStream.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CorLdrFileStream> getRepo() {
		return repo;
	}

}
