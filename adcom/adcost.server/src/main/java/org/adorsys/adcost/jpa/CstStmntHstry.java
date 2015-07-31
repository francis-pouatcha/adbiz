package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstEntityHstry;
import org.adorsys.javaext.description.Description;

@Entity
public class CstStmntHstry extends CoreAbstEntityHstry {

	private static final long serialVersionUID = -1473016410145349937L;

	/*
	 * The status of this statement.
	 */
	@Column
	@Description("CstStmnt_costStatus_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private CstStmntStatus stmntStatus;

	public CstStmntStatus getStmntStatus() {
		return stmntStatus;
	}

	public void setStmntStatus(CstStmntStatus stmntStatus) {
		this.stmntStatus = stmntStatus;
	}

}
