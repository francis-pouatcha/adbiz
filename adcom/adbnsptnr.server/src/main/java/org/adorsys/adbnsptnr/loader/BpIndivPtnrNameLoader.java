package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.rest.BpBnsPtnrEJB;
import org.adorsys.adbnsptnr.rest.BpIndivPtnrNameEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpIndivPtnrNameLoader extends
		AbstractObjectLoader<BpIndivPtnrName> {

	@Inject
	private BpIndivPtnrNameEJB ejb;
	
	@Inject
	private BpBnsPtnrEJB ptnrEJB;

	@Override
	protected BpIndivPtnrName newObject() {
		return new BpIndivPtnrName();
	}

	public BpIndivPtnrName findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpIndivPtnrName create(BpIndivPtnrName entity) {
		BpBnsPtnr bnsPtnr = ptnrEJB.findByIdentif(entity.getPtnrNbr());
		if(bnsPtnr!=null){
			bnsPtnr.setIndivPtnrName(entity);
			bnsPtnr = ptnrEJB.update(bnsPtnr);
			return bnsPtnr.getIndivPtnrName();
		}
		return entity;
	}

	public BpIndivPtnrName update(BpIndivPtnrName found) {
		return ejb.update(found);
	}

	public BpIndivPtnrName deleteById(String id) {
		return ejb.deleteById(id);
	}

}
