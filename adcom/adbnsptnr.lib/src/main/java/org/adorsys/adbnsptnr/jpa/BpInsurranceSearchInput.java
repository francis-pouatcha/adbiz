package org.adorsys.adbnsptnr.jpa;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class BpInsurranceSearchInput
extends CoreAbstIdentifObjectSearchInput<BpInsurrance> {}