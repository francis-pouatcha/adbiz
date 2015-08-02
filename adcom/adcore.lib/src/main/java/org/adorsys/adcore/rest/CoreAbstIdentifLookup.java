package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreSortOrder;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public abstract class CoreAbstIdentifLookup<E extends CoreAbstIdentifObject> {

	protected abstract CoreAbstIdentifRepo<E> getRepo();
	protected abstract Class<E> getEntityClass();

	@Inject
	private EntityManager em;
	protected EntityManager getEntityManager(){
		return em;
	};

	public E findById(String id) {
		return getRepo().findBy(id);
	}

	public E findByIdentif(String identif) {
		if(StringUtils.isBlank(identif)) return null;
		return getRepo().findOptionalByIdentif(identif);
	}
	
	public Long countByIdentif(String identif){
		return getRepo().findByIdentif(identif).count();
	}
	
	public Long countByIdentifBetween(String identifStart, String identifEnd){
		return getRepo().findByIdentifBetween(identifStart, identifEnd).count();
	}
	public List<E> findByIdentifBetween(String identifStart, String identifEnd, int first, int max){
		return getRepo().findByIdentifBetween(identifStart, identifEnd).firstResult(first).maxResults(max).getResultList();
	}

	public Long countByIdentifGreaterThan(String idStart){
		return getRepo().findByIdentifGreaterThan(idStart).count();		
	}
	public List<E> findByIdentifGreaterThan(String idStart, int firstResult, int maxResult){
		return getRepo().findByIdentifGreaterThan(idStart).orderAsc("identif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByIdentifGreaterThanEquals(String idStart){
		return getRepo().findByIdentifGreaterThanEquals(idStart).count();				
	}
	public List<E> findByIdentifGreaterThanEquals(String idStart, int firstResult, int maxResult){
		return getRepo().findByIdentifGreaterThanEquals(idStart).orderAsc("identif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByIdentifLessThan(String idEnd){
		return getRepo().findByIdentifLessThan(idEnd).count();		
	}
	public List<E> findByIdentifLessThan(String idEnd, int firstResult, int maxResult){
		return getRepo().findByIdentifLessThan(idEnd).orderDesc("identif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByIdentifLessThanEquals(String idEnd){
		return getRepo().findByIdentifLessThanEquals(idEnd).count();				
	}
	public List<E> findByIdentifLessThanEquals(String idEnd, int firstResult, int maxResult){
		return getRepo().findByIdentifLessThanEquals(idEnd).orderDesc("identif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}
	
	//==============================================================================================//
	// 																								//			
	// 									CONTAINER IDENTIF											//
	//==============================================================================================//
	public List<E> findByCntnrIdentif(String cntnrIdentif, int start, int max) {
		return getRepo().findByCntnrIdentif(cntnrIdentif).firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByCntnrIdentifAsc(String cntnrIdentif, int start, int max) {
		return getRepo().findByCntnrIdentif(cntnrIdentif).orderAsc(cntnrIdentif).firstResult(start).maxResults(max).getResultList();
	}
	public List<E> findByCntnrIdentifDesc(String cntnrIdentif, int start, int max) {
		return getRepo().findByCntnrIdentif(cntnrIdentif).orderDesc(cntnrIdentif).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByCntnrIdentif(String cntnrIdentif){
		return getRepo().findByCntnrIdentif(cntnrIdentif).count();
	}
	
	public Long countByCntnrIdentifBetween(String identifStart, String identifEnd){
		return getRepo().findByCntnrIdentifBetween(identifStart, identifEnd).count();
	}
	public List<E> findByCntnrIdentifBetween(String identifStart, String identifEnd, int first, int max){
		return getRepo().findByCntnrIdentifBetween(identifStart, identifEnd).firstResult(first).maxResults(max).getResultList();
	}

	public Long countByCntnrIdentifGreaterThan(String identifStart){
		return getRepo().findByCntnrIdentifGreaterThan(identifStart).count();		
	}
	public List<E> findByCntnrIdentifGreaterThan(String identifStart, int firstResult, int maxResult){
		return getRepo().findByCntnrIdentifGreaterThan(identifStart).orderAsc("cntnrIdentif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByCntnrIdentifGreaterThanEquals(String identifStart){
		return getRepo().findByCntnrIdentifGreaterThanEquals(identifStart).count();				
	}
	public List<E> findByCntnrIdentifGreaterThanEquals(String identifStart, int firstResult, int maxResult){
		return getRepo().findByCntnrIdentifGreaterThanEquals(identifStart).orderAsc("cntnrIdentif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByCntnrIdentifLessThan(String identifEnd){
		return getRepo().findByCntnrIdentifLessThan(identifEnd).count();		
	}
	public List<E> findByCntnrIdentifLessThan(String identifEnd, int firstResult, int maxResult){
		return getRepo().findByCntnrIdentifLessThan(identifEnd).orderDesc("cntnrIdentif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}

	public Long countByCntnrIdentifLessThanEquals(String identifEnd){
		return getRepo().findByCntnrIdentifLessThanEquals(identifEnd).count();				
	}
	public List<E> findByCntnrIdentifLessThanEquals(String identifEnd, int firstResult, int maxResult){
		return getRepo().findByCntnrIdentifLessThanEquals(identifEnd).orderDesc("cntnrIdentif").firstResult(firstResult).maxResults(maxResult).getResultList();		
	}
	
	//==============================================================================================//
	
	public List<E> listAll(int start, int max) {
		return getRepo().findAll(start, max);
	}

	public Long count() {
		return getRepo().count();
	}

	public List<E> findBy(E entity, int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findBy(entity, start, max, attributes);
	}

	public Long countBy(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().count(entity, attributes);
	}

	public List<E> findByLike(E entity, int start, int max,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().findByLike(entity, start, max, attributes);
	}

	public Long countByLike(E entity,
			SingularAttribute<E, ?>[] attributes) {
		return getRepo().countLike(entity, attributes);
	}
	
	/*
	 * This shall not be static.
	 */
	private final String FIND_CUSTOM_QUERY = "SELECT e FROM "+ getEntityClass().getSimpleName() +" AS e";
	private final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM "+ getEntityClass().getSimpleName() +" AS e";
	
	protected static final String whereClause = " WHERE ";
	protected static final String andClause = " AND ";
	protected static final String orderBy = "ORDER BY e.";
	protected static final String orderASC = " ASC";
	protected static final String orderDESC = " DESC";

	
	protected StringBuilder preprocessQuery(String findOrCount, CoreAbstIdentifObjectSearchInput<E> searchInput){
		E entity = searchInput.getEntity();

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())) whereSet = prep(whereSet, qBuilder, "e.cntnrIdentif=:cntnrIdentif");
		
		if(searchInput.getValueDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.valueDt >= :valueDtFrom");
		if(searchInput.getValueDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.valueDt <= :valueDtTo");

		if(searchInput.getIdentifFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.identif >= :identifFrom");
		if(searchInput.getIdentifTo()!=null) whereSet = prep(whereSet, qBuilder, "e.identif <= :identifTo");
		
		return qBuilder;
	}

	protected StringBuilder preprocessSort(CoreAbstIdentifObjectSearchInput<E> searchInput, StringBuilder qBuilder){
		List<CoreSortOrder> sortFieldNames = searchInput.getSortFieldNames();
		Set<String> processedFields =  new HashSet<String>();
		Class<E> bsnsObjClass = getEntityClass();
		for (CoreSortOrder coreSortOrder : sortFieldNames) {
			String fieldName = validateSortField(bsnsObjClass, processedFields, coreSortOrder.getFieldName());
			if(StringUtils.isBlank(fieldName)) continue;
			qBuilder.append(orderBy).append(fieldName);
			if(coreSortOrder.getASC()){
				qBuilder.append(orderASC);	
			} else {
				qBuilder.append(orderDESC);	
			}
		}
		return qBuilder;
	}
	
	protected String validateSortField(Class<E> bsnsObjClass, Set<String> processedFields, String fieldName){
		fieldName = StringUtils.trim(fieldName);
		if(StringUtils.isBlank(fieldName)) return null;
		if(processedFields.contains(fieldName)) return null;
		Field field = FieldUtils.getField(bsnsObjClass, fieldName, true);
		if(field==null) return null;
		
		processedFields.add(fieldName);
		return fieldName;
	}
	
	protected void setParameters(CoreAbstIdentifObjectSearchInput<E> searchInput, Query query)
	{
		E entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())) query.setParameter("cntnrIdentif", entity.getCntnrIdentif());

		if(searchInput.getValueDtFrom()!=null) query.setParameter("valueDtFrom", searchInput.getValueDtFrom());
		if(searchInput.getValueDtTo()!=null) query.setParameter("valueDtTo", searchInput.getValueDtTo());
		
		if(searchInput.getIdentifFrom()!=null) query.setParameter("identifFrom", searchInput.getIdentifFrom());
		if(searchInput.getIdentifTo()!=null) query.setParameter("identifTo", searchInput.getIdentifTo());
	}	

	public List<E> findCustom(CoreAbstIdentifObjectSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		qBuilder = preprocessSort(searchInput, qBuilder);
		TypedQuery<E> query = getEntityManager().createQuery(qBuilder.toString(), getEntityClass());
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
	}

	public Long countCustom(CoreAbstIdentifObjectSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = getEntityManager().createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}
	
	protected boolean prep(boolean whereSet, StringBuilder qBuilder, String whereOp){
		if(!whereSet){
			qBuilder.append(whereClause);
			whereSet = true;
		} else {
			qBuilder.append(andClause);
		}
		qBuilder.append(whereOp);
		return whereSet;
	}	
	
	protected String suffix(String section) {
		if(StringUtils.isBlank(section)) return section;
		if(!StringUtils.endsWith(section, "%")) return section + "%";
		return section;
	}

	protected String prefixSuffix(String section) {
		if(StringUtils.isBlank(section)) return section;
		if(!StringUtils.endsWith(section, "%")) return section + "%";
		if(!StringUtils.startsWith(section, "%")) return "%" + section;
		return section;
	}
}
