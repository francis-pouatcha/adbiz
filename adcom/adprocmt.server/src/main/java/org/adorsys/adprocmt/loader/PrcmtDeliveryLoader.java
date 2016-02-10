package org.adorsys.adprocmt.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.xls.CoreAbstBsnsObjectLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adprocmt.api.PrcmtDeliveryManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDlvry2OuEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvry2POEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryLoader extends CoreAbstBsnsObjectLoader<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> {

	@EJB
	private PrcmtDeliveryManager manager;
	@EJB
	private PrcmtDeliveryLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;
	@EJB
	private PrcmtDlvry2OuEJB prcmtDlvry2OuEjb;
	@EJB
	private PrcmtDlvry2POEJB prcmtDlvry2POEjb;
	
	@Override
	protected CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> getManager() {
		return manager;
	}

	@Override
	protected PrcmtDelivery newObject() {
		return new PrcmtDeliveryExcel();
	}

	@Override
	protected CoreAbstLoader<PrcmtDelivery> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	public PrcmtDelivery save(PrcmtDelivery entity, List<PropertyDesc> fields) {
		Date now = new Date();
		PrcmtDelivery prcmtDelivery = new PrcmtDelivery();
		prcmtDelivery.copyFrom(entity);
		prcmtDelivery.setValueDt(now);
		prcmtDelivery = super.save(entity, fields);
		if(!(entity instanceof PrcmtDeliveryExcel)) return prcmtDelivery;
		
		PrcmtDeliveryExcel excelEntity = (PrcmtDeliveryExcel) entity;
		

		List<PrcmtDlvry2Ou> ouList= new ArrayList<PrcmtDlvry2Ou>();
		String rcvngOrgUnits = excelEntity.getRcvngOrgUnits();
		if(StringUtils.isNotBlank(rcvngOrgUnits)){
			String[] rcvngOrgUnitArray = StringUtils.split(rcvngOrgUnits,',');
			for (String rcvngOrgUnit : rcvngOrgUnitArray) {
				if(StringUtils.isBlank(rcvngOrgUnit)) continue;
				int rcvngOrgUnitPercentage = getPercentage(rcvngOrgUnit);
				String rcvngOrgUnitName = getUnitName(rcvngOrgUnit);
				PrcmtDlvry2Ou prcmtDlvry2Ou = new PrcmtDlvry2Ou();
				prcmtDlvry2Ou.setCntnrIdentif(prcmtDelivery.getIdentif());
				prcmtDlvry2Ou.setRcvngOrgUnit(rcvngOrgUnitName);
				prcmtDlvry2Ou.setQtyPct(BigDecimal.valueOf(rcvngOrgUnitPercentage));
				prcmtDlvry2Ou.setValueDt(now);
				ouList.add(prcmtDlvry2Ou);
			}
			checkConsistent(ouList, rcvngOrgUnits);
		}

		List<PrcmtDlvry2PO> poList = new ArrayList<PrcmtDlvry2PO>();
		String procmtOrderNbrs = excelEntity.getProcmtOrderNbrs();
		if(StringUtils.isNotBlank(procmtOrderNbrs)){
			String[] procmtOrderNbrArray = StringUtils.split(procmtOrderNbrs);
			for (String procmtOrderNbr : procmtOrderNbrArray) {
				if(StringUtils.isBlank(procmtOrderNbr)) continue;
				PrcmtDlvry2PO prcmtDlvry2PO = new PrcmtDlvry2PO();
				prcmtDlvry2PO.setCntnrIdentif(prcmtDelivery.getIdentif());
				prcmtDlvry2PO.setPoNbr(procmtOrderNbr.trim());
				prcmtDlvry2PO.setValueDt(now);
				poList.add(prcmtDlvry2PO);
			}
		}
		
		for (PrcmtDlvry2Ou prcmtDlvry2Ou : ouList) {
			prcmtDlvry2OuEjb.create(prcmtDlvry2Ou);
		}
		
		for (PrcmtDlvry2PO prcmtDlvry2PO : poList) {
			prcmtDlvry2POEjb.create(prcmtDlvry2PO);
		}
		
		return prcmtDelivery;
	}
	
	private void checkConsistent(List<PrcmtDlvry2Ou> ouList, String rcvngOrgUnits) {
		Set<String> ouNameSet = new HashSet<String>();
		BigDecimal total = BigDecimal.ZERO;
		for (PrcmtDlvry2Ou prcmtDlvry2Ou : ouList) {
			if(ouNameSet.contains(prcmtDlvry2Ou.getRcvngOrgUnit()))
				throw new IllegalStateException("Can not list the same orgunit two times. Original string: " + rcvngOrgUnits + " - duplicate org unit " + prcmtDlvry2Ou.getRcvngOrgUnit());
			
			ouNameSet.add(prcmtDlvry2Ou.getRcvngOrgUnit());
			total = BigDecimalUtils.sum(total, prcmtDlvry2Ou.getQtyPct());
		}
		if(!BigDecimalUtils.strictEquals(total, BigDecimalUtils.HUNDRED))
			throw new IllegalStateException("Sum of percentage must be 100. Original string: " + rcvngOrgUnits + " - Sum of parts " + total.intValue() + ". But format shall be orgUnitNbr(20),orgUnitNbr(80) or just orgUnitNbr for 100%.");
			
	}

	private int getPercentage(String parsedOrgUnit){
		String percentageStr = StringUtils.substringBetween(parsedOrgUnit, "(", ")");
		if(StringUtils.isBlank(percentageStr)) return 100;
		if(!StringUtils.isNumeric(percentageStr)) throw new IllegalStateException("Recieving organization unit poorly foramted model orgUnitNbr(20),orgUnitNbr(80) or just orgUnitNbr for 100% : but was " + parsedOrgUnit);
		return Integer.parseInt(percentageStr.trim());
	}
	
	private String getUnitName(String parsedOrgUnit){
		String result = StringUtils.substringBefore(parsedOrgUnit, "(");
		if(StringUtils.isBlank(result)) throw new IllegalStateException("Recieving organization unit poorly foramted model orgUnitNbr(20),orgUnitNbr(80) or just orgUnitNbr for 100% : but was " + parsedOrgUnit);
		return result.trim();
	}
}
