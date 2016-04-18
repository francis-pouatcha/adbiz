package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * Indentif is the pthnNbr;
 * 
 * @author fpo
 *
 */
@Entity
@Description("BpBnsPtnr_description")
public class BpBnsPtnr extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -9038535636658903372L;

	@Column
	@Description("BpBnsPtnr_ptnrType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private BpPtnrType ptnrType;

	@Column
	@Description("BpBnsPtnr_ctryOfRsdnc_description")
	private String ctryOfRsdnc;

	@Column
	@Description("BpBnsPtnr_fullName_description")
	private String fullName;

	public BpPtnrType getPtnrType() {
		return this.ptnrType;
	}

	public void setPtnrType(final BpPtnrType ptnrType) {
		this.ptnrType = ptnrType;
	}

	public String getCtryOfRsdnc() {
		return this.ctryOfRsdnc;
	}

	public void setCtryOfRsdnc(final String ctryOfRsdnc) {
		this.ctryOfRsdnc = ctryOfRsdnc;
	}

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identif must be set as identifier prior to saving this object");
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}