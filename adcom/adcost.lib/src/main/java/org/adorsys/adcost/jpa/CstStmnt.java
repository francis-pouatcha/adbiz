package org.adorsys.adcost.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.apache.commons.lang3.StringUtils;

/**
 * A statement gathers costs/profits assigned to a bearer.
 * 
 * - THe statement number is the identif.
 * - The statement type is the txType
 * - The src cost center is the orgIdentif / ouIdentif.
 * - The dest cost center is the bsnsPartner / bsnsPrtnrOU
 * - the stmntSlipNbr is the docNbr.
 * 
 * @author francis
 *
 */
@Entity
@Description("CstStmnt_description")
public class CstStmnt extends CoreAbstBsnsObject {

	private static final long serialVersionUID = 5712985206096922961L;

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(identif)) throw new IllegalStateException("Identifier must be set before creation");
		return identif;
	}
}