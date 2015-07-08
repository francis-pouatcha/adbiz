package org.adorsys.adbase.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "BaseLoginConfiguration")
@Description("LoginConfiguration_description")
public class LoginConfiguration extends CoreAbstTimedData {

	private static final long serialVersionUID = 5322018832893287944L;

	@Column
	@Description("LoginConfiguration_loginName_description")
	@NotNull
	private String loginName;

	@Column
	@Description("LoginConfiguration_maxRebate_description")
	@NotNull
	private BigDecimal maxRebate;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(loginName);
		if (maxRebate == null)
			maxRebate = BigDecimal.ZERO;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}

	public BigDecimal getMaxRebate() {
		return maxRebate;
	}

	public void setMaxRebate(BigDecimal maxRebate) {
		this.maxRebate = maxRebate;
	}

	public void addRebate(BigDecimal rebate) {
		if (this.maxRebate == null)
			this.maxRebate = BigDecimal.ZERO;
		this.maxRebate = this.maxRebate.add(rebate);
	}

	@Override
	protected String makeIdentif() {
		return loginName;
	}

}