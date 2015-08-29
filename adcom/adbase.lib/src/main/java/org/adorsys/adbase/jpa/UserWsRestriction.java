package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity 
@Table(name="BaseUserWsRestrct")
@Description("UserWsRestriction_description")
public class UserWsRestriction extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 5562787996333849175L;

	@Column
	@Description("UserWsRestriction_restId_description")
	@NotNull
	private String restId;

	@Column
	@Description("UserWsRestriction_value_description")
	private String value;

	@Column
	@Description("UserWsRestriction_wsIdentif_description")
	@NotNull
	private String wsIdentif;

	public String getRestId() {
		return this.restId;
	}

	public void setRestId(final String restId) {
		this.restId = restId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getWsIdentif() {
		return this.wsIdentif;
	}

	public void setWsIdentif(final String wsIdentif) {
		this.wsIdentif = wsIdentif;
	}

	@Override
	protected String makeIdentif() {
		return wsIdentif+"_"+restId;
	}

}