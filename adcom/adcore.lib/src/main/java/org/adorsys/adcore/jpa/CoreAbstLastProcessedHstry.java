package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Stores the identifier of the last processed history object.
 * 
 * The process that does this also takes care of cleaning up pass records.
 * The process look like. 
 * 1- Check a list of histories to make sure they are
 * processed. 
 * 2- Then create the last processed record with the oldest entry.
 * 3- The deleted processed record for all younger than the one created.
 * 
 * - The cntnrIdentif is the identifier of the entity processed
 * - The identif is the id of history processed
 * - The valueDt is the date this record was written
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstLastProcessedHstry extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 6660826865900714094L;
	
	/*
	 * used for partial ordering so we can allow for parallel processing.
	 */
	@Column
	private int seqNbr;

	public int getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(int seqNbr) {
		this.seqNbr = seqNbr;
	}
	
}