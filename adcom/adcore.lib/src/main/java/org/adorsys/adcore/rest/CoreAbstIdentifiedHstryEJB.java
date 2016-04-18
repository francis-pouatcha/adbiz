package org.adorsys.adcore.rest;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.event.EntityArchivedEvent;
import org.adorsys.adcore.event.EntityCreatedEvent;
import org.adorsys.adcore.event.EntityDeletedEvent;
import org.adorsys.adcore.event.EntityUpdatedEvent;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifHstry;
import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.apache.commons.lang3.StringUtils;

public abstract class CoreAbstIdentifiedHstryEJB<H extends CoreAbstIdentifHstry, E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifObjectHstryRepo<H> getRepo();
	protected abstract H newHstryObj();
	
	@Inject
	private AdcomUser callerPrincipal;

	public H create(H entity) {
		H saved = getRepo().save(entity);
		return saved;
	}

	public H deleteById(String id) {
		H entity = getRepo().findBy(id);
		if (entity != null) {
			getRepo().remove(entity);
		}
		return entity;
	}
	
	public void deleteByEntIdentif(String invtryNbr, int first, int max) {
		List<H> list = getRepo().findByEntIdentif(invtryNbr).firstResult(first).maxResults(max).getResultList();
		for (H hstry : list) {
			getRepo().remove(hstry);
		}
	}

	public H update(H entity) {
		return getRepo().save(attach(entity));
	}

	protected H attach(H entity) {
		if (entity == null)
			return null;
		return entity;
	}
	
	public void handleEntityCreatedEvent(@Observes @EntityCreatedEvent E entity) {
		H h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.CREATED.name());
		h.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		h.setProcStep(CoreProcStepEnum.INITIATING.name());
		create(h);
	}
	public void handleEntityDeletedEvent(@Observes @EntityDeletedEvent E entity) {
		H h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.DELETED.name());
		h.setHstryType(CoreHistoryTypeEnum.DELETED.name());
		h.setProcStep(CoreProcStepEnum.DELETING.name());
		create(h);
	}
	public void handleEntityUpdatedEvent(@Observes @EntityUpdatedEvent E entity) {
		H h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.MODIFIED.name());
		h.setHstryType(CoreHistoryTypeEnum.MODIFIED.name());
		h.setProcStep(CoreProcStepEnum.MODIFYING.name());
		create(h);
	}
	public void handleEntityArchivedEvent(@Observes @EntityArchivedEvent E entity) {
		H h = newHstry(entity);
		h.setEntStatus(CoreProcessStatusEnum.ARCHIVED.name());
		h.setHstryType(CoreHistoryTypeEnum.ARCHIVED.name());
		h.setProcStep(CoreProcStepEnum.ARCHIVING.name());
		create(h);
	}
	
	protected H newHstry(E entity){
		H h = newHstryObj();
		h.setCntnrIdentif(entity.getCntnrIdentif());
		h.setEntIdentif(entity.getIdentif());
		h.setHstryDt(new Date());
		h.setOrignLogin(callerPrincipal.getLoginName());
		h.setOriginUserName(StringUtils.isBlank(callerPrincipal.getFullName())?callerPrincipal.getLoginName():callerPrincipal.getFullName());
		h.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		h.setAddtnlInfo(printHstryInfo(entity));
		return h;
	}
	public String printHstryInfo(E entity){
		return entity.toString();
	}
	
}
