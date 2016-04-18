package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.event.EntityHstryEvent;
import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.jpa.CoreAbstEntityJob;
import org.adorsys.adcore.jpa.CoreAbstEntityStep;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstBsnsObjectHstryEJB<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsItem, H extends CoreAbstBsnsObjectHstry, J extends CoreAbstEntityJob, S extends CoreAbstEntityStep, C extends CoreAbstEntityCstr>
		extends CoreAbstIdentifiedHstryEJB<H, E> {

	@Inject
	@EntityHstryEvent
	private Event<H> entityHistoryEvent;

	@Inject
	private AdcomUser callerPrincipal;

	protected abstract CoreAbstBsnsObjInjector<E, I, H, J, S, C> getInjector();

	protected abstract H newHstryObj();

	public H create(H entity) {
		getRepo().persist(entity);
		fireEvent(entity);
		return entity;
	}

	public H deleteById(String id) {
		H entity = getRepo().findBy(id);
		if (entity != null) {
			getRepo().remove(entity);
		}
		return entity;
	}

	public void deleteByEntIdentif(String invtryNbr, int first, int max) {
		List<H> list = getRepo().findByEntIdentif(invtryNbr).firstResult(first)
				.maxResults(max).getResultList();
		for (H hstry : list) {
			getRepo().remove(hstry);
		}
	}

	public H createHstry(String entIdentif, String txStatus, String hstryType,
			String processingStep, String comment, String addInfo) {
		return createItemHstry(entIdentif, null, txStatus, hstryType,
				processingStep, comment, addInfo);
	}

	public H createItemHstry(String entIdentif, String itemIdentif,
			String txStatus, String hstryType, String processingStep,
			String comment, String addInfo) {
		H hstry = newHstryObj();
		hstry.setComment(comment);
		hstry.setAddtnlInfo(addInfo);
		hstry.setEntIdentif(entIdentif);
		hstry.setCntnrIdentif(entIdentif);
		hstry.setItemIdentif(itemIdentif);
		hstry.setEntStatus(txStatus);
		hstry.setHstryDt(new Date());
		hstry.setHstryType(hstryType);

		hstry.setOrignLogin(callerPrincipal.getLoginName());
		hstry.setOriginUserName(StringUtils.isBlank(callerPrincipal.getFullName())?callerPrincipal.getLoginName():callerPrincipal.getFullName());
		hstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		hstry.setProcStep(processingStep);
		return create(hstry);
	}

	protected void fireEvent(H hstry) {
		entityHistoryEvent.fire(hstry);
	}

	public void archiveById(String id) {
		deleteById(id);
	}
}
