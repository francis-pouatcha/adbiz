package org.adorsys.adcore.jpa;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.utils.FluentArray;
import org.apache.commons.lang3.StringUtils;

/**
 * This is an entity with a business key. The business key is an information
 * we can use to uniquely retries the object.
 * 
 * The abstract makeIdentif() method allow the customization of this identifier.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstIdentifObject extends CoreAbstEntity {

	private static final long serialVersionUID = -6116613863022444415L;

	/*
	 * THe identifier is unique and we can use if in the delta spikes
	 * findOptionalByIdentif
	 */
	@NotNull
	@Column(unique=true)
	protected String identif;
	
	/*
	 * Describes the container of this object. Generally container are used to 
	 * manage access to an object. To access an object, you need to have access to
	 * the container of that object.
	 * 
	 * Since object can chage owner, we wont use container to prefix the identifier
	 * of an object. Unless explicitly done so by the programmer.
	 * 
	 * A null container means that this object is not contained by another object.
	 * 
	 */
	protected String cntnrIdentif;

	@Temporal(TemporalType.TIMESTAMP)
	private Date valueDt;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId())) {
			if (StringUtils.isBlank(identif)) {
				identif = makeIdentif();
			}
			setId(identif);
		} else if (StringUtils.isBlank(identif)){
			identif = getId();
		}
	}

	public String getIdentif() {
		return identif;
	}

	public void setIdentif(String identif) {
		this.identif = identif;
	}

	public String getCntnrIdentif() {
		return cntnrIdentif;
	}

	public void setCntnrIdentif(String cntnrIdentif) {
		this.cntnrIdentif = cntnrIdentif;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [identif=" + getIdentif() + "]";
	}

	protected abstract String makeIdentif();
	
	protected static final Collection<String> coreAbstIdentifObjectExcludeFields =FluentArray.asList("identif").addAll(coreAbstEntityExcludedFields).toCol();
	public boolean contentEquals(CoreAbstEntity rhs){
		return contentEqualsInternal(rhs, coreAbstIdentifObjectExcludeFields);
	}

	public Date getValueDt() {
		return valueDt;
	}

	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}
	
	public void cleanId(){
		setId(null);
		setIdentif(null);
		setVersion(0);
	}

	public void cleanAllIdentifFields(){
		cleanId();
		setCntnrIdentif(null);
		setValueDt(null);
	}
}