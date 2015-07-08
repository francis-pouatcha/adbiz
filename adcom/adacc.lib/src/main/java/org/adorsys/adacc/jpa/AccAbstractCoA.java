package org.adorsys.adacc.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;

/**
 * Persistent representation of a chart of account.
 * 
 * @author francis
 *
 */
@MappedSuperclass
@Description("AccCoA_description")
public abstract class AccAbstractCoA extends CoreAbstIdentifData {

	private static final long serialVersionUID = 9035517709783493286L;

	/*
	 * The company name.
	 */
	@Column
	@Description("AccCoA_cpyName_description")
	private String cpyName;

	/*
	 * The company's short name.
	 */
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