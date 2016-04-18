package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;

/**
 * identif is cdrNbr
 * 
 * @author fpo
 *
 */
@Entity
@Description("CdrDrctSales_description")
public class CdrDrctSalesArchive extends CoreAbstBsnsObject {
	private static final long serialVersionUID = 6750753138236709985L;
}