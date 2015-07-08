package org.adorsys.adbase.jpa;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

/**
 * 
 * @author guymoyo
 *
 *         the purpose of this class is to save, the global config of the
 *         application
 */
@Entity
@Table(name = "BaseConfig")
@Description("BaseConfig_description")
public class BaseConfig extends CoreAbstTimedData {

	private BigDecimal nbrOfItemsPrcmtOrder = new BigDecimal(100);

	private PrintMode printMode;

	public BaseConfig() {
	}

	public BaseConfig(BigDecimal nbrOfItemsPrcmtOrder, PrintMode printMode) {
		this.nbrOfItemsPrcmtOrder = nbrOfItemsPrcmtOrder;
		this.printMode = printMode;
	}

	public PrintMode getPrintMode() {
		return printMode;
	}

	public void setPrintMode(PrintMode printMode) {
		this.printMode = printMode;
	}

	public BigDecimal getNbrOfItemsPrcmtOrder() {
		return nbrOfItemsPrcmtOrder;
	}

	public void setNbrOfItemsPrcmtOrder(BigDecimal nbrOfItemsPrcmtOrder) {
		this.nbrOfItemsPrcmtOrder = nbrOfItemsPrcmtOrder;
	}

	@Override
	protected String makeIdentif() {
		return "CONFIG";
	}
	
	// to get default config if there is no Base Config in DB
	public static BaseConfig defaultConfig() {
		return new BaseConfig(BigDecimal.TEN, PrintMode.PREVIEW);
	}

}