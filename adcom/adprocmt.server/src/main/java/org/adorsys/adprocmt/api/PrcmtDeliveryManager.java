package org.adorsys.adprocmt.api;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;
import org.adorsys.adprocmt.rest.PrcmtDlvry2OuEJB;

@Stateless
public class PrcmtDeliveryManager  extends CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>>{
	@Inject
	private PrcmtDeliveryInjector injector;
	
	@Inject
	private PrcmtDlvry2OuEJB dlvry2OuEJB;

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDelivery_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<PrcmtDelivery> newSearchInput() {
		return new PrcmtDeliverySearchInput();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<PrcmtDelivery> newSearchResult(
			Long size, List<PrcmtDelivery> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtDelivery> searchInput) {
		return new PrcmtDeliverySearchResult(size, resultList, searchInput);
	}

	@Override
	public PrcmtDelivery initiateBsnsObj(PrcmtDelivery bsnsObj) {
		bsnsObj = super.initiateBsnsObj(bsnsObj);
		return bsnsObj;
	}

	@POST
	@Path("/{identif}/ous")
	@Produces({ "application/json"})
	public PrcmtDlvry2Ou addOu(@PathParam("identif") String identif, PrcmtDlvry2Ou ou) throws AdRestException {
		PrcmtDlvry2Ou prcmtDlvry2Ou = new PrcmtDlvry2Ou();
		prcmtDlvry2Ou.setCntnrIdentif(identif);
		prcmtDlvry2Ou.setRcvngOrgUnit(ou.getRcvngOrgUnit());
		prcmtDlvry2Ou.setQtyPct(ou.getQtyPct());
		return dlvry2OuEJB.create(prcmtDlvry2Ou);
	}

	@DELETE
	@Path("/{dlvryIdentif}/ous/{ouIdentif}")
	@Produces({ "application/json"})
	public PrcmtDlvry2Ou removeOu(@PathParam("dlvryIdentif") String dlvryIdentif, @PathParam("ouIdentif") String ouIdentif) throws AdRestException {
		String identif = PrcmtDlvry2Ou.toId(dlvryIdentif, ouIdentif);
		return dlvry2OuEJB.deleteById(id)Id(id)
	}
}
