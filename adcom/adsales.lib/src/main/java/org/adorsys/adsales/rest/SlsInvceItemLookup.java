package org.adorsys.adsales.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.repo.SlsInvceItemRepository;

@Stateless
public class SlsInvceItemLookup extends CoreAbstBsnsItemLookup<SlsInvceItem>{
	@Inject
	private SlsInvceItemRepository repository;

	@Override
	protected CoreAbstBsnsItemRepo<SlsInvceItem> getBsnsRepo() {
		return repository;
	}

}
