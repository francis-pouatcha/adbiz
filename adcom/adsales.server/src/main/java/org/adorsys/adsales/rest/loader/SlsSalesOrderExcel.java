package org.adorsys.adsales.rest.loader;

import org.adorsys.adsales.jpa.SlsAbstractSalesOrder;

public class SlsSalesOrderExcel extends SlsAbstractSalesOrder {
	private static final long serialVersionUID = 1557537966561489049L;
	
	private String ctmrNbr;
	private String ctmRoleInSO;
	private String insNbr;
	private String insRolsInSo;
	
	public String getCtmrNbr() {
		return ctmrNbr;
	}
	public void setCtmrNbr(String ctmrNbr) {
		this.ctmrNbr = ctmrNbr;
	}
	public String getCtmRoleInSO() {
		return ctmRoleInSO;
	}
	public void setCtmRoleInSO(String ctmRoleInSO) {
		this.ctmRoleInSO = ctmRoleInSO;
	}
	public String getInsNbr() {
		return insNbr;
	}
	public void setInsNbr(String insNbr) {
		this.insNbr = insNbr;
	}
	public String getInsRolsInSo() {
		return insRolsInSo;
	}
	public void setInsRolsInSo(String insRolsInSo) {
		this.insRolsInSo = insRolsInSo;
	}
}
