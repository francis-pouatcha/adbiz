package org.adorsys.adcore.jpa;

import javax.persistence.MappedSuperclass;

/**
 * Store the last entity of a population processed by a batch.
 * 
 * This only works if the identifier of the collection processed
 * if ordered.
 * 
 * - The cntnrIdentif is the identifier of the entity being processed
 * - The identif is the id of history processed
 * - valueDt is the date to which this history was processed.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstProcessedHstry extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 6660826865900714094L;
}