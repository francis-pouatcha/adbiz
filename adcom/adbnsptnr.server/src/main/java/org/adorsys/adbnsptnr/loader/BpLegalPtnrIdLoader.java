package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.rest.BpBnsPtnrEJB;
import org.adorsys.adbnsptnr.rest.BpLegalPtnrIdEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpLegalPtnrIdLoader extends AbstractObjectLoader<BpLegalPtnrId> {

	@Inject
	private BpLegalPtnrIdEJB ejb;

	@Inject
	private BpBnsPtnrEJB ptnrEJB;
	
	@Override
	protected BpLegalPtnrId newObject() {
		return new BpLegalPtnrId();
	}

	public BpLegalPtnrId findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpLegalPtnrId create(BpLegalPtnrId entity) {
		BpBnsPtnr bnsPtnr = ptnrEJB.findByIdentif(entity.getPtnrNbr());
		if(bnsPtnr!=null){
			bnsPtnr.setLegalPtnrId(entity);
			bnsPtnr = ptnrEJB.update(bnsPtnr);
			return bnsPtnr.getLegalPtnrId();
		}
		return entity;
	}

	public BpLegalPtnrId update(BpLegalPtnrId found) {
		return ejb.update(found);
	}

	public BpLegalPtnrId deleteById(String id) {
		return ejb.deleteById(id);
	}

}
