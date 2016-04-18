package org.adorsys.adbnsptnr.jpa;

import java.util.ArrayList;
import java.util.List;

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
public class BpPtnrCreditDtlsSearchInput
extends CoreAbstIdentifObjectSearchInput<BpBnsPtnr> {}