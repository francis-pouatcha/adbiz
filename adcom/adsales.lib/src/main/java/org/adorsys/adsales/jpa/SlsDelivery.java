package org.adorsys.adsales.jpa;

import java.util.Date;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreCurrencyEnum;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("PrcmtDelivery_description")
public class SlsDelivery extends SlsAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;

	public void fillDataFromOrder(SlsSalesOrder salesOrder) {
	
		this.setDlvryCur(StringUtils.isNotBlank(salesOrder.getSoCur())?salesOrder.getSoCur():CoreCurrencyEnum.XAF.name());
		this.setDlvryDt(new Date());
//		this.setDlvrySlipNbr(salesOrder.getSoNbr());
		this.setGrossSPPreTax(salesOrder.getGrossSPPreTax());
		this.setNetAmtToPay(salesOrder.getNetAmtToPay());
		this.setNetSPPreTax(salesOrder.getNetSPPreTax());
		this.setNetSPTaxIncl(salesOrder.getNetSPTaxIncl());
		this.setNetSalesAmt(salesOrder.getNetSalesAmt());
		this.setOrderDt(salesOrder.getSoDt());
		this.setPymtDscntAmt(salesOrder.getPymtDscntAmt());
		this.setPymtDscntPct(salesOrder.getPymtDscntPct());
		this.setRdngDscntAmt(salesOrder.getRdngDscntAmt());
		this.setRebate(salesOrder.getRebate());
		this.setVatAmount(salesOrder.getVatAmount());
	}

	
	public void fillDataFromInvoice(SlsInvoice slsInvoice) {
		
		this.setDlvryCur(StringUtils.isNotBlank(slsInvoice.getInvceCur())?slsInvoice.getInvceCur():CoreCurrencyEnum.XAF.name());
		this.setDlvryDt(new Date());
//		this.setDlvrySlipNbr(slsInvoice.getPoNbr());
		this.setGrossSPPreTax(slsInvoice.getGrossSPPreTax());
		this.setNetAmtToPay(slsInvoice.getNetAmtToPay());
		this.setNetSPPreTax(slsInvoice.getNetSPPreTax());
		this.setNetSPTaxIncl(slsInvoice.getNetSPTaxIncl());
		this.setNetSalesAmt(slsInvoice.getNetSalesAmt());
		this.setOrderDt(slsInvoice.getInvceDt());
		this.setPymtDscntAmt(slsInvoice.getPymtDscntAmt());
		this.setPymtDscntPct(slsInvoice.getPymtDscntPct());
		this.setRdngDscntAmt(slsInvoice.getRdngDscntAmt());
		this.setRebate(slsInvoice.getRebate());
		this.setVatAmount(slsInvoice.getVatAmount());
	}
}