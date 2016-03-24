package org.adorsys.adcore.jpa;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * Used to abstract the storage of a byte stream in the data base.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class CoreAbstIdentifByteChunk extends CoreAbstIdentifObject {
	private static final long serialVersionUID = 3535037082488805363L;

	private int size;
	
	private int position;
	
	/*
	 * The content of this  byte chunk;
	 */
	@Lob
	@Basic(fetch=FetchType.LAZY)
    private byte[] content;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
