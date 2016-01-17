package org.adorsys.adcore.b2.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Identifier part of a B2Entity. This part is supposed to be cached and clustered by the 
 * JPA-Implementation's native caching and clustering mechanism. 
 * 
 * The main intention of this indirection is to reduce the quantity of data exchanged 
 * between cluster nodes.
 * 
 * Whenever an entity is updated, the version number of that entity is automatically increased by
 * persistence manager. This is, the version number of the entity and the one of the header are always
 * kept synchronized.
 * 
 * Only the header is held in the jpa second level cache. If this class is clustered, the clustering
 * layer will propagated to oder cluster nodes.
 * 
 * Nodes will keep the information and use it when the user tries to access the entity and only return the entity
 * if the version of the entity is higher than the version of the header. If not it will force reloading of the
 * entity by the cache loader.
 * 
 * @author fpo
 *
 */
@Entity
public class B2PersHeader {
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id = null;

	@Version
	@Column(name = "version")
	private int version = 0;

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
}
