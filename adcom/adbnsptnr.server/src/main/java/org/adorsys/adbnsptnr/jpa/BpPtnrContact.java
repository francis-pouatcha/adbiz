package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrContact_description")
public class BpPtnrContact extends CoreAbstTimedData {

	private static final long serialVersionUID = -6876392363267204213L;

	@Column
	@Description("BpPtnrContact_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpPtnrContact_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("BpPtnrContact_cntctRole_description")
	@Enumerated(EnumType.STRING)
	private BpPtnrContactRole cntctRole;

	@Column
	@Description("BpPtnrContact_cntctIndex_description")
	@NotNull
	private Integer cntctIndex;
	
	@Column
	@Description("BpPtnrContact_description_description")
	@Size(max = 256)
	private String description;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public BpPtnrContactRole getCntctRole() {
		return this.cntctRole;
	}

	public void setCntctRole(final BpPtnrContactRole cntctRole) {
		this.cntctRole = cntctRole;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getCntctIndex() {
		return cntctIndex;
	}

	public void setCntctIndex(Integer cntctIndex) {
		this.cntctIndex = cntctIndex;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr + "_" + (cntctRole==null?"null":cntctRole.name()) + "_" + langIso2 + "_" + cntctIndex;
	}
}