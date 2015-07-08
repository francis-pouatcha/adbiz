package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseOrgUnit")
@Description("OrgUnit_description")
public class OrgUnit extends CoreAbstTimedData {

	private static final long serialVersionUID = -4042784869338352973L;

	@Column
	@Description("OrgUnit_ctryIso3_description")
	@NotNull
	private String ctryIso3;

	@Column
	@Description("OrgUnit_typeIdentif_description")
	@NotNull
	private String typeIdentif;

	@Column
	@Description("OrgUnit_parentIdentif_description")
	private String parentIdentif;
	
	@Column
	@Description("OrgUnit_fullName_description")
	@NotNull
	private String fullName;

	@Column
	@Description("OrgUnit_shortName_description")
	@NotNull
	private String shortName;
	
	// Data for printing
	@Transient
	private OrgContact contact;

	public String getCtryIso3() {
		return this.ctryIso3;
	}

	public void setCtryIso3(final String ctryIso3) {
		this.ctryIso3 = ctryIso3;
	}

	public String getTypeIdentif() {
		return this.typeIdentif;
	}

	public void setTypeIdentif(final String typeIdentif) {
		this.typeIdentif = typeIdentif;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public String getParentIdentif() {
		return parentIdentif;
	}

	public void setParentIdentif(String parentIdentif) {
		this.parentIdentif = parentIdentif;
	}

	public OrgContact getContact() {
		return contact;
	}

	public void setContact(OrgContact contact) {
		this.contact = contact;
	}

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(identif))
			throw new IllegalStateException("Identifier must be set explicitely.");
		return getIdentif();
	}

	@Override
	public String toString() {
		return "OrgUnit [ctryIso3=" + ctryIso3 + ", typeIdentif=" + typeIdentif
				+ ", fullName=" + fullName + ", shortName=" + shortName + "]";
	}
}