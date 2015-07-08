package org.adorsys.adprocmt.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adcore.enums.CoreProcStepEnum;
import org.adorsys.adcore.enums.CoreProcessStatusEnum;
import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjLifeCycle;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adcore.rest.CoreAbstEntityJobEJB;
import org.adorsys.adcore.rest.CoreAbstEntityJobLookup;
import org.adorsys.adcore.rest.CoreAbstEntityStepEJB;
import org.adorsys.adcore.rest.CoreAbstEntityStepLookup;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.api.DeliveryInfo;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryJob;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryStep;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2PORepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryEJB extends CoreAbstBsnsObjectEJB<
	PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, 
	PrcmtDeliveryJob, PrcmtDeliveryStep, PrcmtDeliveryCstr> {

	@Inject
	private PrcmtDeliveryRepository repository;

	@Inject
	private PrcmtDlvry2PORepository dlvry2poRepository;

	@Inject
	private PrcmtDlvry2OuRepository dlvry2OuRepository;
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private PrcmtDeliveryHstryEJB hstryEjb;
	@Inject
	private PrcmtDeliveryHstryLookup hstryLookup;
	
	@Inject
	private EntityManager em ;
	
	@Inject
	private PrcmtDeliveryLookup lookup;
	
	@EJB
	private PrcmtDeliveryEJB ejb;
	
	@Inject
	private PrcmtDlvryItemLookup itemLookup;
	@Inject
	private PrcmtDlvryItemEJB itemEjb;
	
	@Inject
	private PrcmtDeliveryHstryLifeCycle lifeCycle;

	public PrcmtDelivery create(PrcmtDelivery entity) {
		if (StringUtils.isBlank(entity.getDlvryNbr())) {
			entity.setDlvryNbr(SequenceGenerator
					.getSequence(SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE));
		}

		entity.setId(entity.getDlvryNbr());
		entity.setIdentif(entity.getDlvryNbr());

		entity = repository.save(attach(entity));
		return entity;
	}
	
	public PrcmtDelivery createCustom(PrcmtDelivery entity) {
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		entity.setCreatingUsr(currentLoginName);
		entity.setCreationDt(now);
		if(entity.getDlvryDt()==null) entity.setDlvryDt(now);
		entity.setDlvryStatus(CoreProcessStatusEnum.ONGOING.name());
		entity = create(entity);
		createInitialDeliveryHistory(entity);
		return entity;
	}
	
	private void createInitialDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setComment(CoreHistoryTypeEnum.INITIATED.name());
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(CoreHistoryTypeEnum.INITIATED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(CoreProcStepEnum.INITIATING.name());
		deliveryHstryEJB.create(deliveryHstry);
	}

	public PrcmtDlvry2PO addProcOrder(PrcmtDelivery entity, String poNbr) {
		PrcmtDlvry2PO dlvryPO = new PrcmtDlvry2PO();
		dlvryPO.setDlvryNbr(entity.getDlvryNbr());
		dlvryPO.setPoNbr(poNbr);
		dlvryPO = dlvry2poRepository.save(dlvryPO);

		return dlvryPO;
	}

	public PrcmtDlvry2Ou addOrgUnit(PrcmtDelivery entity, String rcvngOrgUnit,
			BigDecimal qtyPct) {
		PrcmtDlvry2Ou dlvry2Ou = new PrcmtDlvry2Ou();
		dlvry2Ou.setDlvryNbr(entity.getDlvryNbr());
		dlvry2Ou.setRcvngOrgUnit(rcvngOrgUnit);
		dlvry2Ou.setQtyPct(qtyPct);
		dlvry2Ou = dlvry2OuRepository.save(dlvry2Ou);

		return dlvry2Ou;
	}

	public PrcmtDelivery deleteById(String id) {
		PrcmtDelivery entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);

			List<PrcmtDlvry2PO> pos = dlvry2poRepository.findByDlvryNbr(entity
					.getDlvryNbr());
			for (PrcmtDlvry2PO po : pos) {
				dlvry2poRepository.remove(po);
			}
			List<PrcmtDlvry2Ou> ous = dlvry2OuRepository.findByDlvryNbr(entity
					.getDlvryNbr());
			for (PrcmtDlvry2Ou ou : ous) {
				dlvry2OuRepository.remove(ou);
			}
		}
		return entity;
	}

	public PrcmtDlvry2PO deleteProcOrder(String id) {
		PrcmtDlvry2PO po = dlvry2poRepository.findBy(id);
		if (po != null) {
			dlvry2poRepository.remove(po);
		}
		return po;
	}

	public PrcmtDlvry2Ou deleteOrgUnit(String id) {
		PrcmtDlvry2Ou ou = dlvry2OuRepository.findBy(id);
		if (ou != null) {
			dlvry2OuRepository.remove(ou);
		}
		return ou;
	}

	public PrcmtDelivery update(PrcmtDelivery entity) {
		entity = repository.save(attach(entity));
		return entity;
	}

	public PrcmtDlvry2Ou updateOrgUnit(PrcmtDlvry2Ou ou) {
		ou = dlvry2OuRepository.save(ou);
		return ou;
	}

	public PrcmtDelivery findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDelivery> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDelivery> findBy(PrcmtDelivery entity, int start, int max,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public PrcmtDlvry2PO findProcOrder(String dlvryNbr, String poNbr) {
		return dlvry2poRepository.findBy(PrcmtDlvry2PO.toId(dlvryNbr, poNbr));
	}

	public PrcmtDlvry2Ou findOrgUnit(String dlvryNbr, String rcvngOrgUnit) {
		return dlvry2OuRepository.findBy(PrcmtDlvry2Ou.toId(dlvryNbr,
				rcvngOrgUnit));
	}

	public Long countBy(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDelivery> findByLike(PrcmtDelivery entity, int start,
			int max, SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDelivery attach(PrcmtDelivery entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<PrcmtDlvry2PO> listProcOrders(String dlvryNbr) {
		return dlvry2poRepository.findByDlvryNbr(dlvryNbr);
	}

	public List<PrcmtDlvry2Ou> listOrgUnits(String dlvryNbr) {
		return dlvry2OuRepository.findByDlvryNbr(dlvryNbr);
	}

	public PrcmtDelivery findByIdentif(String identif) {
		List<PrcmtDelivery> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}

	public List<String> findClosingDeliveries(int qty) {
		return repository.findByDlvryStatus(CoreProcessStatusEnum.CLOSING.name()).maxResults(qty).getResultList();
	}

	public PrcmtDeliverySearchResult findCustom(PrcmtDeliverySearchInput searchInput) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p FROM PrcmtDelivery AS p WHERE 1=1 ");
			
		if(StringUtils.isNotBlank(searchInput.getEntity().getDlvrySlipNbr())){
			sb.append("AND LOWER(p.dlvrySlipNbr) LIKE LOWER(:dlvrySlipNbr) ");
		}
		if(searchInput.getDateMin() != null){
			sb.append("AND p.creationDt<=:dateMax ");
		}
		if(searchInput.getDateMax() != null){
			sb.append("AND p.creationDt>=:dateMin");
		}
		
		String query = sb.toString();	
		Query createQuery = em.createQuery(query);
		if(searchInput.getDateMin() != null){
			createQuery.setParameter("dateMin", searchInput.getDateMin());
		}
		if(searchInput.getDateMax() != null){
			createQuery.setParameter("dateMax", searchInput.getDateMax());
		}
		if(StringUtils.isNotBlank(searchInput.getEntity().getDlvrySlipNbr())){
			String dlvrySlipNbr = searchInput.getEntity().getDlvrySlipNbr()+"%";
			createQuery.setParameter("dlvrySlipNbr", dlvrySlipNbr);
		}

		@SuppressWarnings("unchecked")
		List<PrcmtDelivery> resultList = createQuery.getResultList();
		
		if(searchInput.getStart() >= 0){
			createQuery.setFirstResult(searchInput.getStart());
		}
		if(searchInput.getMax() > 0){
			createQuery.setMaxResults(searchInput.getMax());
		}
		@SuppressWarnings("unchecked")
		List<PrcmtDelivery> resultList2 = createQuery.getResultList();

		PrcmtDeliverySearchResult prcmtDeliverySearchResult = new PrcmtDeliverySearchResult();
		prcmtDeliverySearchResult.setCount(Long.valueOf(resultList.size()));
		prcmtDeliverySearchResult.setSearchInput(searchInput);
		prcmtDeliverySearchResult.setResultList(resultList2);

		return  prcmtDeliverySearchResult;	
	}

	@Override
	protected CoreAbstBsnsObjectRepo<PrcmtDelivery> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<PrcmtDelivery> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsObjectEJB<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtDeliveryJob, PrcmtDeliveryStep, PrcmtDeliveryCstr> getEjb() {
		return ejb;
	}

	@Override
	protected CoreAbstBsnsItemLookup<PrcmtDlvryItem> getItemLookup() {
		return itemLookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<PrcmtDlvryItem> getItemEjb() {
		return itemEjb;
	}

	@Override
	protected CoreAbstBsnsObjectHstryLookup<PrcmtDeliveryHstry> getHistoryLookup() {
		return hstryLookup;
	}

	@Override
	protected CoreAbstBsnsObjectHstryEJB<PrcmtDeliveryHstry> getHistoryEjb() {
		return hstryEjb;
	}

	@Override
	protected String getSequenceGeneratorPrefix() {
		return SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE;
	}

	@Override
	protected TermWsUserPrincipal getCallerPrincipal() {
		return securityUtil.getCallerPrincipal();
	}

	@Override
	protected String prinHstryInfo(PrcmtDelivery entity) {
		return DeliveryInfo.prinInfo(entity);
	}

	@Override
	protected CoreAbstBsnsObjLifeCycle<PrcmtDeliveryHstry> getLifeCycle() {
		return lifeCycle;
	}

	@Override
	protected CoreAbstEntityJobEJB<PrcmtDeliveryJob> getJobEjb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreAbstEntityJobLookup<PrcmtDeliveryJob> getJobLookup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreAbstEntityStepEJB<PrcmtDeliveryStep> getStepEjb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreAbstEntityStepLookup<PrcmtDeliveryStep> getStepLookup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreAbstEntityCstrEJB<PrcmtDeliveryCstr> getCstrEjb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CoreAbstEntityCstrLookup<PrcmtDeliveryCstr> getCstrLookup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCurrentProcessIdentif() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
