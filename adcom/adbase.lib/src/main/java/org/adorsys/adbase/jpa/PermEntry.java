package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity 
@Table(name="BasePermEntry")
@Description("PermEntry_description")
public class PermEntry extends CoreAbstTimedData {

	private static final long serialVersionUID = -8997465407110640284L;

	@Column
	@Description("PermEntry_roleIdentif_description")
	@NotNull
	private String roleIdentif;

	@Column
	@Description("PermEntry_permIdentif_description")
	@NotNull
	private String permIdentif;

	@Column
	@Description("PermEntry_action_description")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private PermActionEnum action;

	public String getRoleIdentif() {
		return this.roleIdentif;
	}

	public void setRoleIdentif(final String roleIdentif) {
		this.roleIdentif = roleIdentif;
	}

	public String getPermIdentif() {
		return this.permIdentif;
	}

	public void setPermIdentif(final String permIdentif) {
		this.permIdentif = permIdentif;
	}

	public PermActionEnum getAction() {
		return this.action;
	}

	public void setAction(final PermActionEnum action) {
		this.action = action;
	}

	@Override
	protected String makeIdentif() {
		return roleIdentif + "_" + permIdentif + "_" + action.name();
	}
}