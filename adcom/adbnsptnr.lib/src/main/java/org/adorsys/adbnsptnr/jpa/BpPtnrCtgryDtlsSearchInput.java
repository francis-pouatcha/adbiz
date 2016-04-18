package org.adorsys.adbnsptnr.jpa;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstLangObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class BpPtnrCtgryDtlsSearchInput
extends CoreAbstLangObjectSearchInput<BpPtnrCtgryDtls>{}