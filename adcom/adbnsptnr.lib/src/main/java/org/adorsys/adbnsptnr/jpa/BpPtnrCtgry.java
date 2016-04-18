package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * The parent code is the container identifier.
 * 
 * @author fpo
 *
 */
@Entity
@Description("BpPtnrCtgry_description")
public class BpPtnrCtgry extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -5239368389118117504L;

	@Column
	@Description("BpPtnrCtgry_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpPtnrCtgry_ptnrRole_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private BpPtnrRole ptnrRole;

	public BpPtnrRole getPtnrRole() {
		return this.ptnrRole;
	}

	public void setPtnrRole(final BpPtnrRole ptnrRole) {
		this.ptnrRole = ptnrRole;
	}

	@Override
	protected String makeIdentif() {
		return ptnrRole.name() + "_" + ctgryCode;
	}
}