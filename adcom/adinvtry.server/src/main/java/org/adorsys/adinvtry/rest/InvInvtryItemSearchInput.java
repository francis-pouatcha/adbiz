package org.adorsys.adinvtry.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adinvtry.jpa.InvInvtryItem;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class InvInvtryItemSearchInput extends CoreAbstBsnsItemSearchInput<InvInvtryItem>{}
