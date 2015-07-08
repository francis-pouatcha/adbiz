package org.adorsys.adprocmt.jpa;

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
public class PrcmtPOItemSearchInput extends CoreAbstBsnsItemSearchInput<PrcmtPOItem>{}
