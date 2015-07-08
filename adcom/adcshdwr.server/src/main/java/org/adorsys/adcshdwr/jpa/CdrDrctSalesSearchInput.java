package org.adorsys.adcshdwr.jpa;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class CdrDrctSalesSearchInput extends CoreAbstBsnsObjectSearchInput<CdrDrctSales>{}
