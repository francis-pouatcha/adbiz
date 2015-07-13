package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;

/**
 * The moved qty is the target qty.
 * @author francis
 *
 */
@MappedSuperclass
@Description("StkMvnt_description")
public abstract class StkAbstractStkMvnt extends CoreAbstBsnsItem {

	private static final long serialVersionUID = -4624920888946443298L;

	// Doc numbers will be a string separated list of doc types and nbrs.
	// like: INVOICE:XXX,PROX_ORDER:YYY,INVENTORY:ZZZ,CASH_RECEIPT:CCC
	@Column
	@Description("StkMvnt_origDocNbrs_description")
	private String origDocNbrs;

	// The originating process. Sales, Inventory, Procurement
	@Column
	@Description("StkAbstractStockQty_origProcs_description")
	@NotNull
	private String origProcs;

	// The identifier of the origin process.
	@Column
	@Description("StkAbstractStockQty_origProcsNbr_description")
	@NotNull
	private String origProcsNbr;

	public String getOrigDocNbrs() {
		return origDocNbrs;
	}

	public void setOrigDocNbrs(String origDocNbrs) {
		this.origDocNbrs = origDocNbrs;
	}

	public String getOrigProcs() {
		return origProcs;
	}

	public void setOrigProcs(String origProcs) {
		this.origProcs = origProcs;
	}

	public String getOrigProcsNbr() {
		return origProcsNbr;
	}

	public void setOrigProcsNbr(String origProcsNbr) {
		this.origProcsNbr = origProcsNbr;
	}
}