package org.adorsys.adcore.b2.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Store a chunk of byte in the database. Generally raw data processed
 * by the deserialization subsystem.
 * 
 * @author fpo
 *
 */
@Entity
public class B2ByteChunk {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id = null;

	@Version
	@Column(name = "version")
	private int version = 0;

	/*
	 * The identifier of the container of this chunk.
	 */
	@NotNull
	protected String ctnrId;

	/*
	 * The identifier of the container of this chunk.
	 */
	@NotNull
	protected String rootId;

	/*
	 * The previous item.
	 */
	protected String prevId;

	/*
	 * The next id
	 */
	protected String nextId;
	
	/*
	 * The size of this byte chunk.
	 */
	private int size;
	
	/*
	 * The position in the containing byte stream.
	 */
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCtnrId() {
		return ctnrId;
	}

	public void setCtnrId(String ctnrId) {
		this.ctnrId = ctnrId;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getPrevId() {
		return prevId;
	}

	public void setPrevId(String prevId) {
		this.prevId = prevId;
	}

	public String getNextId() {
		return nextId;
	}

	public void setNextId(String nextId) {
		this.nextId = nextId;
	}
}
