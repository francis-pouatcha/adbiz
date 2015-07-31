package org.adorsys.adcore.jpa;

import javax.persistence.MappedSuperclass;

/**
 * Locks an entity being processed.
 * 
 * - cntnrIdentif is the identifier of the process owner (The thread doing the work)
 * - identif is the identifier of the entity being processed. An entity exists exception means that another thread is doing the work.
 * - valueDt is the lease time. After this time, processing can be recovered.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstProcessingEntity extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 8628963593036183617L;
}