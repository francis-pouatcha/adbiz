package org.adorsys.adsales.jpa;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class SlsDlvryItemSearchInput extends CoreAbstBsnsItemSearchInput<SlsDlvryItem>{}
