package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstTxctedItem;

/**
 * A statement item identifies a single assignment in a statement.
 * 
 * - The number of the containing statement is the cntnrIDentif
 * - THe otem number is the identif.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstStmntItem_description")
public class CstStmntItem extends CoreAbstTxctedItem {

	private static final long serialVersionUID = -8466036435151817125L;

	/*
	 * The identifier of the source activity center.
	 */
	@Column
	@Description("CstStmntItem_srcCtrNbr_description")
	@NotNull
	private String srcCtrNbr;

	/*
	 * The identifier of the source bearer.
	 */
	@Column
	@Description("CstStmntItem_srcBrrNbr_description")
	@NotNull
	private String srcBrrNbr;
	
	/*
	 * The identifier of the activity center receiving this statement.
	 */
	@Column
	@Description("CstStmntItem_destCtrNbr_description")
	@NotNull
	private String destCtrNbr;

	/*
	 * The bearer receiving the this statement item
	 */
	@Column
	@Description("CstStmntItem_destBrrNbr_description")
	@NotNull
	private String destBrrNbr;

	public String getSrcCtrNbr() {
		return srcCtrNbr;
	}

	public void setSrcCtrNbr(String srcCtrNbr) {
		this.srcCtrNbr = srcCtrNbr;
	}

	public String getSrcBrrNbr() {
		return srcBrrNbr;
	}

	public void setSrcBrrNbr(String srcBrrNbr) {
		this.srcBrrNbr = srcBrrNbr;
	}

	public String getDestCtrNbr() {
		return destCtrNbr;
	}

	public void setDestCtrNbr(String destCtrNbr) {
		this.destCtrNbr = destCtrNbr;
	}

	public String getDestBrrNbr() {
		return destBrrNbr;
	}

	public void setDestBrrNbr(String destBrrNbr) {
		this.destBrrNbr = destBrrNbr;
	}
}