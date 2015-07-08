package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrAccntBlnce_description")
public class BpPtnrAccntBlnce extends AbstractMvmtData {

	private static final long serialVersionUID = 8463167904715344200L;

	@Column
	@Description("BpPtnrAccntBlnce_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpPtnrAccntBlnce_side_description")
	@Enumerated(EnumType.ORDINAL)
	private BpAccBalanceSide side;

	@Column
	@Description("BpPtnrAccntBlnce_accBalance_description")
	private BigDecimal accBalance;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpPtnrAccntBlnce_balanceDt_description")
	@NotNull
	private Date balanceDt;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public BpAccBalanceSide getSide() {
		return this.side;
	}

	public void setSide(final BpAccBalanceSide side) {
		this.side = side;
	}

	public BigDecimal getAccBalance() {
		return this.accBalance;
	}

	public void setAccBalance(final BigDecimal accBalance) {
		this.accBalance = accBalance;
	}

	public Date getBalanceDt() {
		return this.balanceDt;
	}

	public void setBalanceDt(final Date balanceDt) {
		this.balanceDt = balanceDt;
	}
}