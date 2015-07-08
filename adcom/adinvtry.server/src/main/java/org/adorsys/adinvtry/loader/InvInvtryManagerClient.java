package org.adorsys.adinvtry.loader;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.api.InvInvtryManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class InvInvtryManagerClient {

	@Inject
	private InvInvtryManager invtryManager;
	
	@Inject
	private SecurityUtil securityUtil; 

//	InvInvtryHolder invtryHolder = new InvInvtryHolder();
	
	private InvInvtry invInvtry;
	
	public void saveInvtry(InvInvtryExcel invtryExcel){
		InvInvtry invtry = new InvInvtry();
		invtryExcel.copyTo(invtry);
		// New Holder
//		this.invtryHolder = new InvInvtryHolder();
		invInvtry = invtryManager.prepareInventory(invtry);
//		this.invtryHolder.setInvtry(invtry);
		// Process org units.
	}
	
	public void saveInvtryItem(InvInvtryItemExcel invtryItemExcel){
		InvInvtryItem invtryItem = new InvInvtryItem();
		invtryItemExcel.copyTo(invtryItem);
		if(StringUtils.isBlank(invtryItem.getLotPic())){
			String lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
			invtryItem.setLotPic(lotPic);
		}
		invtryItem.setBsnsObjNbr(invInvtry.getInvtryNbr());
		invtryManager.addItem(invtryItem);
//		InvInvtryItemHolder invtryItemHolder = new InvInvtryItemHolder();
//		invtryItemHolder.setInvtryItem(invtryItem);
		
//		invtryHolder.getInvtryItemHolders().add(invtryItemHolder);
		
//		if(invtryHolder.getInvtryItemHolders().size()>=20){
//			InvInvtryHolder updateInvtry = invtryManager.updateInventory(invtryHolder);
//			invtryHolder = new InvInvtryHolder();
//			invtryHolder.setInvtry(updateInvtry.getInvtry());
		
//		}
	}

	public void done() {
		invInvtry = invtryManager.closeInventory(invInvtry);
		invInvtry = invtryManager.postInventory(invInvtry);
		invInvtry = null;
//		invtryHolder = new InvInvtryHolder();
	}
	
//	public InvInvtry update(){
//		return invtryManager.updateInventory(invtryHolder);
//	}
}
