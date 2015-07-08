package org.adorsys.adinvtry.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcore.vo.StringListHolder;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.rest.InvInvtryItemLookup;
import org.adorsys.adinvtry.rest.InvInvtryItemSearchInput;
import org.adorsys.adinvtry.rest.InvInvtryItemSearchResult;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/inventory")
public class InvInvtryManagerEndpoint {

	@Inject
	private InvInvtryManager invtryManager;

	@Inject
	private InvInvtryItemLookup invInvtryItemLookup;

	@PUT
	@Path("/prepare")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtry newInventory(InvInvtry invtry) {
		return invtryManager.prepareInventory(invtry);
	}

	@PUT
	@Path("/update")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtry updateInventory(InvInvtry invtry) {
		return invtryManager.updateInventory(invtry);
	}

	@PUT
	@Path("/close")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtry closeInventory(InvInvtry invtry) {
		return invtryManager.closeInventory(invtry);
	}

	@PUT
	@Path("/validate")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtry validateInventory(InvInvtry invtry) {
		return invtryManager.validateInventory(invtry);
	}

	@PUT
	@Path("/post")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtry postInventory(InvInvtry invtry) {
		return invtryManager.postInventory(invtry);
	}

	@PUT
	@Path("/addItem")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtryItem addItem(InvInvtryItem invtryItem) {
		return invtryManager.addItem(invtryItem);
	}

	@PUT
	@Path("/updateItem")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtryItem updateItem(InvInvtryItem invtryItem) {
		return invtryManager.updateItem(invtryItem);
	}

	@PUT
	@Path("/disableItem")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtryItemSearchResult disableItem(InvInvtryItem invtryItem) {
		invtryManager.disableItem(invtryItem);
		return findByHldrNbrAndSalIndex(invtryItem);
	}

	@PUT
	@Path("/enableItem")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public InvInvtryItemSearchResult enableItem(InvInvtryItem invtryItem) {
		invtryManager.enableItem(invtryItem);
		return findByHldrNbrAndSalIndex(invtryItem);
	}

	private InvInvtryItemSearchResult findByHldrNbrAndSalIndex(
			InvInvtryItem invtryItem) {
		long count = invInvtryItemLookup.countByBsnsObjNbrAndSalIndex(
				invtryItem.getBsnsObjNbr(), invtryItem.getSalIndex()).intValue();
		List<InvInvtryItem> resultList = invInvtryItemLookup
				.findByBsnsObjNbrAndSalIndex(invtryItem.getBsnsObjNbr(),
						invtryItem.getSalIndex(), 0, 100);
		InvInvtryItemSearchInput searchInput = new InvInvtryItemSearchInput();
		searchInput.setEntity(invtryItem);
		searchInput.setMax(100);
		searchInput.setStart(0);
		searchInput.getFieldNames().add("hldrNbr");
		searchInput.getFieldNames().add("salIndex");
		return new InvInvtryItemSearchResult(count, resultList, searchInput);
	}

	/**
	 * Marks the list of inventories listed for merging into the first
	 * inventory.
	 * 
	 * Returns the list of merged inventories. The first one being the
	 * container.
	 * 
	 * @param invtryNbrs
	 * @return
	 */
	@PUT
	@Path("/merge")
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public StringListHolder merge(StringListHolder invtryNbrs) {
		return invtryManager.merge(invtryNbrs);
	}
}