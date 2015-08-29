package org.adorsys.adbase.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
public abstract class BaseAbstractCurrRate extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 1120666861067636937L;

	@Column
	@Description("ConverterCurrRate_srcCurrISO3_description")
	@NotNull
	private String srcCurrISO3;

	@Column
	@Description("ConverterCurrRate_destCurrISO3_description")
	@NotNull
	private String destCurrISO3;

	@Column
	@Description("ConverterCurrRate_exRate_description")
	@NotNull
	private BigDecimal exRate;

	public String getSrcCurrISO3() {
		return this.srcCurrISO3;
	}

	public void setSrcCurrISO3(final String srcCurrISO3) {
		this.srcCurrISO3 = srcCurrISO3;
	}

	public String getDestCurrISO3() {
		return this.destCurrISO3;
	}

	public void setDestCurrISO3(final String destCurrISO3) {
		this.destCurrISO3 = destCurrISO3;
	}

	public BigDecimal getExRate() {
		return this.exRate;
	}

	public void setExRate(final BigDecimal exRate) {
		this.exRate = exRate;
	}

	@Override
	protected String makeIdentif() {
		return srcCurrISO3 + "_" + destCurrISO3;
	}
}