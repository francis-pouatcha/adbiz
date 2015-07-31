package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;

@Entity
public class AptAptmtLogin extends CoreAbstTimedData {

	private static final long serialVersionUID = 8717558433187769255L;

	@Column
	@NotNull
	private String aptmtIdentify;

	@Column
	@NotNull
	private String loginIdentify;

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}

		return super.equals(that);
	}

	public String getAptmtIdentify() {
		return this.aptmtIdentify;
	}

	public void setAptmtIdentify(final String aptmtIdentify) {
		this.aptmtIdentify = aptmtIdentify;
	}

	public String getLoginIdentify() {
		return this.loginIdentify;
	}

	public void setLoginIdentify(final String loginIdentify) {
		this.loginIdentify = loginIdentify;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (aptmtIdentify != null && !aptmtIdentify.trim().isEmpty())
			result += "aptmtIdentify: " + aptmtIdentify;
		if (loginIdentify != null && !loginIdentify.trim().isEmpty())
			result += ", loginIdentify: " + loginIdentify;
		return result;
	}

	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return null;
	}

}