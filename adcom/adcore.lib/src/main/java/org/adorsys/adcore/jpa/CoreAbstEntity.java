package org.adorsys.adcore.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * This class define core entity fields for all other entities.
 * 
 * For this framework, all entity ids are string and self generated.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private String id = null;

	@Version
	@Column(name = "version")
	private int version = 0;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(UUID.randomUUID().toString());
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((CoreAbstEntity) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public void copyTo(CoreAbstEntity target){
		try {
			BeanUtils.copyProperties(target, this);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}

	public void copyFrom(CoreAbstEntity source){
		try {
			BeanUtils.copyProperties(this, source);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected static final Collection<String> coreAbstEntityExcludedFields = Collections.unmodifiableList(Arrays.asList("id","version","class"));
	public void contentEquals(CoreAbstEntity rhs){
		EqualsBuilder.reflectionEquals(this, rhs, coreAbstEntityExcludedFields);
	}
}