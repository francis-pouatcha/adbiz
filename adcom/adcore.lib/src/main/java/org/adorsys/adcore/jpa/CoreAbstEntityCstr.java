package org.adorsys.adcore.jpa;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.DateFormatPattern;

/**
 * This defines a constraint on an entity. This constraint can be :
 * 	- a time based: means the constrain is occurring sometime in the future. In this case the cstrDt is in the future.
 * 	- an instant constraint. Just happened. The cstrDt is equal to the creationDt.
 * 
 * In some case this constraints will help control the processing of entity data. If for example an entity is frozen, the
 * corresponding constraint can prevent any party from updating the entity.
 * 
 * A contraint can be documented by a history entry in the history table of the target entity.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public class CoreAbstEntityCstr extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 7019202452972427220L;

	/*
	 * This is the identifier of the target entity, for which this constraint has been created.
	 */
	@Column
	@NotNull
	private String entIdentif;
	
	/*
	 * True if this constraint is unique in the extent of its entity. In which case
	 * the identifier will be constructed not to allow another constraint object
	 * of the same type for this entity. 
	 */
	@Column
	private Boolean uniqueCstr;

	/*
	 * The type of constraint. See @StandardCstr
	 */
	@Column
	@NotNull
	private String cstrType;

	/*
	 * Addtional detail. For example when the constrain applies to a member. The member name.
	 */
	@Column
	private String cstrDetail;

	@Column
	private String cstrValue;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date validFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date validTo;
	
	@Column
	@NotNull
	private String cstrOwner;

	@Override
	protected String makeIdentif() {
		if(Boolean.TRUE.equals(uniqueCstr)){
			return entIdentif +"_"+ cstrType;
		} else {
			return UUID.randomUUID().toString();
		}
	}

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public String getCstrType() {
		return cstrType;
	}

	public void setCstrType(String cstrType) {
		this.cstrType = cstrType;
	}

	public String getCstrDetail() {
		return cstrDetail;
	}

	public void setCstrDetail(String cstrDetail) {
		this.cstrDetail = cstrDetail;
	}

	public String getCstrValue() {
		return cstrValue;
	}

	public void setCstrValue(String cstrValue) {
		this.cstrValue = cstrValue;
	}

	public Boolean getUniqueCstr() {
		return uniqueCstr;
	}

	public void setUniqueCstr(Boolean uniqueCstr) {
		this.uniqueCstr = uniqueCstr;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getCstrOwner() {
		return cstrOwner;
	}

	public void setCstrOwner(String cstrOwner) {
		this.cstrOwner = cstrOwner;
	}
}