package org.adorsys.adcore.jpa;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class CoreAbstLangObjectSearchInput<E extends CoreAbstLangObject> extends CoreAbstIdentifObjectSearchInput<E> {}
