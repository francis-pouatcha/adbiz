package org.adorsys.adcshdwr.jpa;

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
public class CdrDsArtItemSearchInput extends CoreAbstBsnsItemSearchInput<CdrDsArtItem>{}
