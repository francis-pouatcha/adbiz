package org.adorsys.adcshdwr.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCshDrawerSearchInput;
import org.adorsys.adcshdwr.repo.CdrCshDrawerRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrCshDrawerEJB
{

	/**
	 * The ZERO field.
	 */
	private static final BigDecimal INITIAL_AMT = BigDecimal.ZERO;

	@Inject
	private CdrCshDrawerRepository repository;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private EntityManager em;

	public CdrCshDrawer create(CdrCshDrawer entity)
	{
		return repository.save(attach(entity));
	}

	public CdrCshDrawer deleteById(String id)
	{
		CdrCshDrawer entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CdrCshDrawer update(CdrCshDrawer entity)
	{
		return repository.save(attach(entity));
	}

	public CdrCshDrawer findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrCshDrawer> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrCshDrawer> findBy(CdrCshDrawer entity, int start, int max, SingularAttribute<CdrCshDrawer, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrCshDrawer entity, SingularAttribute<CdrCshDrawer, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrCshDrawer> findByLike(CdrCshDrawer entity, int start, int max, SingularAttribute<CdrCshDrawer, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrCshDrawer entity, SingularAttribute<CdrCshDrawer, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrCshDrawer attach(CdrCshDrawer entity)
	{
		if (entity == null)
			return null;

		return entity;
	}

	public CdrCshDrawer openCshDrawer(CdrCshDrawer cshDrawer) {
		String loginName = securityUtil.getCurrentLoginName();
		if(hasOpenedCshDrawer(loginName)) {
			cshDrawer = findOpenedCshDrawerByCashier(loginName).iterator().next();
		}else {
			if(cshDrawer == null) cshDrawer = new CdrCshDrawer();
			String sequence = SequenceGenerator.getSequence(SequenceGenerator.CASHDRAWER_SEQUENCE_PREFIXE);

			cshDrawer.setCashier(loginName);
			cshDrawer.setCdrNbr(sequence);
			if(cshDrawer.getInitialAmt() == null || cshDrawer.getInitialAmt().intValueExact() < 0) {
				cshDrawer.setInitialAmt(INITIAL_AMT);   
			}
			cshDrawer.setOpngDt(new Date());
			cshDrawer = repository.save(cshDrawer);
		}
		return cshDrawer;
	}
	
	public CdrCshDrawer closeCshDrawer(CdrCshDrawer cshDrawer) throws AdException {
		String loginName = securityUtil.getCurrentLoginName();
		if(cshDrawer == null && !hasOpenedCshDrawer(loginName)) throw new AdException("No cash drawer to close.");
		if(cshDrawer == null && hasOpenedCshDrawer(loginName)) {
			cshDrawer = findOpenedCshDrawerByCashier(loginName).iterator().next();
		}
//		computeCshDrawer(cshDrawer);
		cshDrawer.setClsngDt(new Date());
		cshDrawer.setClosedBy(securityUtil.getCurrentLoginName());
		return repository.save(cshDrawer);
	}
	

	/**
	 * computeCshDrawer.
	 *
	 * @param cshDrawer
	 */
	private void computeCshDrawer(CdrCshDrawer cshDrawer) {}

	/**
	 * hasOpenedCshDrawer.
	 *
	 * @return
	 */
	 private boolean hasOpenedCshDrawer(String cashier) {
		if(StringUtils.isBlank(cashier)) {
			cashier = securityUtil.getCurrentLoginName();
		}
		List<CdrCshDrawer> cshDrawersByCashier = findOpenedCshDrawerByCashier(cashier);
		return !cshDrawersByCashier.isEmpty();
	}

	 
	public List<CdrCshDrawer> findOpenedCshDrawerByCashier(String cashier) {
		return repository.findOpenedCshDrawerByCashier(cashier);
	}
	
	/**
	 * getActiveCshDrawer.
	 *
	 * @return
	 * @throws AdException 
	 */
	public CdrCshDrawer getActiveCshDrawer() throws AdException {
		String loginName = securityUtil.getCurrentLoginName();
		List<CdrCshDrawer> cshDrawers = findOpenedCshDrawerByCashier(loginName);
		if(cshDrawers.isEmpty()) return null;
		if(cshDrawers.size() > 1) throw new AdException("More than one active cash drawer for user "+loginName+"\r, the system"
				+ " can't find the default.");
		return cshDrawers.iterator().next();
	}
	
	public List<CdrCshDrawer> findPreviousCdrCshDrawer() {
		String loginName = securityUtil.getCurrentLoginName();
		List<CdrCshDrawer> previous = repository.findPrevious(loginName);
		return previous;
	}

	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM CdrCshDrawer AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM CdrCshDrawer AS e";
	
	public Long countCustom(CdrCshDrawerSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}

	public List<CdrCshDrawer> findCustom(CdrCshDrawerSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<CdrCshDrawer> query = em.createQuery(qBuilder.toString(), CdrCshDrawer.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
	}
	
	private StringBuilder preprocessQuery(String findOrCount, CdrCshDrawerSearchInput searchInput){
		CdrCshDrawer entity = searchInput.getEntity();	
		
		String whereClause = " WHERE  ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(StringUtils.isNotBlank(entity.getCashier())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.cashier=:cashier");
		}
		if(searchInput.getFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.opngDt>:from");
		}
		if(searchInput.getTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.opngDt<:to");
		}
		if(!securityUtil.hasWorkspace("manager")){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.cashier=:cashier");
		}
		
		return qBuilder;
	}

	
	public void setParameters(CdrCshDrawerSearchInput searchInput, Query query)
	{
		CdrCshDrawer entity = searchInput.getEntity();

		if(StringUtils.isNotBlank(entity.getCashier())){
			query.setParameter("cashier", entity.getCashier());
		}
		if(searchInput.getFrom()!=null){
			query.setParameter("from", searchInput.getFrom());
		}
		if(searchInput.getTo()!=null){
			query.setParameter("to", searchInput.getTo());
		}
		if(!securityUtil.hasWorkspace("manager")){
			query.setParameter("cashier", securityUtil.getCurrentLoginName());
		}
	}
	
}
