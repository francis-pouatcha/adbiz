package org.adorsys.adcore.b2.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * The information on a deleted object. 
 * 
 * If the deletion of a root entity is an expensive transactional operation
 * maybe due to the size of the tree, the initial delete operation can create
 * this operation so batch job can cleanup automatically.
 * 
 *  This object can also be created upfront to schedule expiry of a root entity.
 *  
 *  The identity of the delete marker is the id of the root entity.
 * 
 * @author fpo
 *
 */
@Entity
public class B2DeleteMarker  {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id = null;
	
	@Version
	@Column(name = "version")
	private int version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirDt;
	
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

	public Date getExpirDt() {
		return expirDt;
	}

	public void setExpirDt(Date expirDt) {
		this.expirDt = expirDt;
	}
}
