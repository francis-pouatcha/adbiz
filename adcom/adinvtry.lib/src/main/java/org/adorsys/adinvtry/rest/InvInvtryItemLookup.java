package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemLookup extends CoreAbstBsnsItemLookup<InvInvtryItem>
{

	@Inject
	private InvInvtryItemRepository repository;
	@Override
	protected CoreAbstBsnsItemRepo<InvInvtryItem> getBsnsRepo() {
		return repository;
	}
	@Override
	protected Class<InvInvtryItem> getEntityClass() {
		return InvInvtryItem.class;
	}
}
