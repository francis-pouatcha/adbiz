package org.adorsys.adbase.jpa;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.adcore.annotation.DateFormatPattern;
import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Table(name="BaseConnHist")
@Description("ConnectionHistory_description")
public class ConnectionHistory extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -3655451239124519992L;

	/** The user name. */
	@Column
	@Description("ConnectionHistory_loginName_description")
	private String loginName;

	/** The partner id. */
	@Column
	@Description("ConnectionHistory_terminalName_description")
	private String terminalName;

	/** The workspace id. */
	@Column
	@Description("ConnectionHistory_workspaceId_description")
	private String workspaceId;

	/** The partner id. */
	@Column
	@Description("ConnectionHistory_partnerId_description")
	private String ouId;
	
	/** The ip adress. */
	@Column
	@Description("ConnectionHistory_ipAdress_description")
	private String ipAdress;

	/** The first login date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("ConnectionHistory_firstLoginDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date firstLoginDate;

	/** The last login date. */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("ConnectionHistory_lastLoginDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date lastLoginDate;

	/** The counter. */
	@Column
	@Description("ConnectionHistory_counter_description")
	private BigInteger counter = BigInteger.ZERO;	

	/** The term credential. */
	@Column
	@Description("ConnectionHistory_termCdtl_description")
	private String termCdtl;
	
	@Column
	@Description("ConnectionHistory_terminalId_description")
	private String terminalId;
	
	@Column
	@Description("ConnectionHistory_opr_description")
	private String opr;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public Date getFirstLoginDate() {
		return firstLoginDate;
	}

	public void setFirstLoginDate(Date firstLoginDate) {
		this.firstLoginDate = firstLoginDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public BigInteger getCounter() {
		return counter;
	}

	public void setCounter(BigInteger counter) {
		this.counter = counter;
	}

	public String getTermCdtl() {
		return termCdtl;
	}

	public void setTermCdtl(String termCdtl) {
		this.termCdtl = termCdtl;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public String getOuId() {
		return ouId;
	}

	public void setOuId(String ouId) {
		this.ouId = ouId;
	}

	@Override
	protected String makeIdentif() {
		return UUID.randomUUID().toString();
	}
}
