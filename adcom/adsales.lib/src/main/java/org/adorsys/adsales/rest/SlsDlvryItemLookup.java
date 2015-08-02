package org.adorsys.adsales.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adsales.jpa.SlsDlvryItem;
import org.adorsys.adsales.repo.SlsDlvryItemRepository;

@Stateless
public class SlsDlvryItemLookup extends CoreAbstBsnsItemLookup<SlsDlvryItem>{
	@Inject
	private SlsDlvryItemRepository repository;

	@Override
	protected CoreAbstBsnsItemRepo<SlsDlvryItem> getBsnsRepo() {
		return repository;
	}

}
