package org.adorsys.adcore.b2.jpa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Identifier of a persistent object. This is generally an object stored in the byte database.
 * 
 * @author fpo
 *
 */
@Entity
public class B2PersContent {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id = null;

	@Version
	@Column(name = "version")
	private int version = 0;

	/*
	 * The identifier of the container of this chunk.
	 */
	private String ctnrId;
	
	/*
	 * The root container id. This is the id of the to most object of a tree.
	 * 
	 *  For the root object, the root container id is the id of the object.
	 *  Meaning that the root object is self contained.
	 */
	private String rootId;
	
	private String entType;
	
	/*
	 * In case this object is guarded, the guard id. 
	 */
	private String guardId;
	
	private String idx1;
	
	private String idx2;
	
	private String idx3;
	
	private String idx4;
	
	private String idx5;
	
	private String idx6;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date valueDt;
	
	/*
	 * This will be used as the container id of the byte chunk.
	 */
	private String contentId;
	
	@NotNull
	@OneToOne (cascade=CascadeType.ALL)
	@JoinColumn(name="id", nullable=false, updatable=false)
	private B2PersHeader header=new B2PersHeader();;

	@PrePersist
	@PreUpdate
	public void preSave(){
		// Only set rootId to id if container id is not null
		if(rootId==null && ctnrId==null)rootId=id;
		
		// Update header
		if(header==null) header=new B2PersHeader();
		header.setId(id);
		header.setVersion(version);
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

	public String getGuardId() {
		return guardId;
	}

	public void setGuardId(String guardId) {
		this.guardId = guardId;
	}

	public Date getValueDt() {
		return valueDt;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}

	public String getEntType() {
		return entType;
	}

	public void setEntType(String entType) {
		this.entType = entType;
	}

	public B2PersHeader getHeader() {
		return header;
	}

	public void setHeader(B2PersHeader header) {
		this.header = header;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getIdx1() {
		return idx1;
	}

	public void setIdx1(String idx1) {
		this.idx1 = idx1;
	}

	public String getIdx2() {
		return idx2;
	}

	public void setIdx2(String idx2) {
		this.idx2 = idx2;
	}

	public String getIdx3() {
		return idx3;
	}

	public void setIdx3(String idx3) {
		this.idx3 = idx3;
	}

	public String getIdx4() {
		return idx4;
	}

	public void setIdx4(String idx4) {
		this.idx4 = idx4;
	}

	public String getIdx5() {
		return idx5;
	}

	public void setIdx5(String idx5) {
		this.idx5 = idx5;
	}

	public String getIdx6() {
		return idx6;
	}

	public void setIdx6(String idx6) {
		this.idx6 = idx6;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}	
	
	
}
