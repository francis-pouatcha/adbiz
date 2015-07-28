package org.adorsys.adsales.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.repo.SlsSOItemRepository;

@Stateless
public class SlsSOItemLookup extends CoreAbstBsnsItemLookup<SlsSOItem>{
	@Inject
	private SlsSOItemRepository repository;

	@Override
	protected CoreAbstBsnsItemRepo<SlsSOItem> getBsnsRepo() {
		return repository;
	}

}
