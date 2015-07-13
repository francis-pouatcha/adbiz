package org.adorsys.adcore.jpa;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

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
	public boolean contentEquals(CoreAbstEntity rhs){
		return contentEqualsInternal(rhs, coreAbstEntityExcludedFields);
	}
	protected boolean contentEqualsInternal(CoreAbstEntity rhs, Collection<String> excludedFields){
		try {
			Map<String, Object> describe = PropertyUtils.describe(this);
			Map<String, Object> describe2 = PropertyUtils.describe(rhs);
			Set<Entry<String,Object>> entrySet = describe.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				if(excludedFields.contains(key)) continue;
				Object value = entry.getValue();
				Object value2 = describe2.get(key);
				if(value==null){
					if(value2!=null) return false;
				} else if(value instanceof BigDecimal){
					if(!(value2 instanceof BigDecimal)) return false;
					if(!BigDecimalUtils.numericEquals((BigDecimal)value, (BigDecimal)value2)) return false;
				} else {
					if(!value.equals(value2)) return false;
				}
			}
			return true;
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
}