package org.adorsys.adbase.jpa;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreSearchInput;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class BaseSttlmtOpSearchInput extends CoreSearchInput<BaseProcStep>{}
