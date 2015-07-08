package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity 
@Table(name="BaseOuWsRestrct")
@Description("OuWsRestriction_description")
public class OuWsRestriction extends CoreAbstTimedData {

	private static final long serialVersionUID = -8368460820325030065L;

	@Column
	@Description("OuWsRestriction_restId_description")
	@NotNull
	private String restId;

	@Column
	@Description("OuWsRestriction_value_description")
	private String value;

	@Column
	@Description("OuWsRestriction_wsIdentif_description")
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
		return wsIdentif + "_" + restId;
	}
}