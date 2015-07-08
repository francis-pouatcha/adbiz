package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("AccCoA_description")
public class AccCoA extends CoreAbstTimedData {

	private static final long serialVersionUID = 9035517709783493286L;

	@Column
	@Description("AccCoA_cpyName_description")
	private String cpyName;

	@Column
	@Description("AccCoA_shortName_description")
	private String shortName;

	public String getCpyName() {
		return this.cpyName;
	}

	public void setCpyName(final String cpyName) {
		this.cpyName = cpyName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	@Override
	protected String makeIdentif() {
		return shortName;
	}
}