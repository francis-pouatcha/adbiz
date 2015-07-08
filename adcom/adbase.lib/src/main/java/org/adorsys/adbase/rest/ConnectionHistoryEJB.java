package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.ConnectionHistorySearchInput;
import org.adorsys.adbase.repo.ConnectionHistoryRepository;
import org.adorsys.adbase.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class ConnectionHistoryEJB {

	@Inject
	private ConnectionHistoryRepository repository;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private EntityManager em;
	
	public ConnectionHistory create(ConnectionHistory entity) {
		return repository.save(attach(entity));
	}

	public ConnectionHistory deleteById(String id) {
		ConnectionHistory entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public ConnectionHistory update(ConnectionHistory entity) {
		return repository.save(attach(entity));
	}

	public ConnectionHistory findById(String id) {
		return repository.findBy(id);
	}

	public List<ConnectionHistory> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<ConnectionHistory> findBy(ConnectionHistory entity, int start,
			int max, SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ConnectionHistory entity,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ConnectionHistory> findByLike(ConnectionHistory entity,
			int start, int max,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ConnectionHistory entity,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ConnectionHistory attach(ConnectionHistory entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public ConnectionHistory findByLoginName(String loginName) {
		List<ConnectionHistory> resultList = repository.findByLoginName(loginName).orderDesc("lastLoginDate").maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	public List<ConnectionHistory> findAllByLoginName(String loginName) {
		List<ConnectionHistory> resultList = repository.findByLoginName(loginName).orderDesc("lastLoginDate").getResultList();
		return resultList;
	}
	

	public List<ConnectionHistory> searchConnectionHistorys(ConnectionHistorySearchInput searchInput) {
		ConnectionHistory entity = searchInput.getEntity();
		int start = searchInput.getStart();
		int max = searchInput.getMax();
		
		String ouId = entity.getOuId();
		String loginName = entity.getLoginName();
		if(StringUtils.isBlank(ouId)) {
			ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		}
		StringBuilder qBuilder = new StringBuilder("SELECT e FROM ConnectionHistory AS e WHERE e.ouId LIKE(:ouId)");
		boolean isLoginNameSet = false;
		if(StringUtils.isNotBlank(loginName)) {
			qBuilder.append(" AND LOWER(e.loginName) LIKE(LOWER(:loginName))");
			isLoginNameSet = true;
		}
		TypedQuery<ConnectionHistory> query = em.createQuery(qBuilder.toString(), ConnectionHistory.class);
		query.setParameter("ouId", ouId+"%");
		if(isLoginNameSet) {
			query.setParameter("loginName", "%"+loginName+"%");
		}
		if(start < 0) { start = 0;}
		query.setFirstResult(start);
		if(max >= 1) {
			query.setMaxResults(max);
		}
		return query.getResultList();
	}

	public Long countConnectionHistorys(ConnectionHistorySearchInput searchInput) {
		ConnectionHistory entity = searchInput.getEntity();
		String ouId = entity.getOuId();
		String loginName = entity.getLoginName();
		if(StringUtils.isBlank(ouId)) {
			ouId = securityUtil.getCurrentOrgUnit().getIdentif();
		}
		StringBuilder qBuilder = new StringBuilder("SELECT COUNT(e) FROM ConnectionHistory AS e WHERE e.ouId LIKE(:ouId)");
		boolean isLoginNameSet = false;
		if(StringUtils.isNotBlank(loginName)) {
			qBuilder.append(" AND LOWER(e.loginName) LIKE(LOWER(:loginName))");
			isLoginNameSet = true;
		}
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		query.setParameter("ouId", ouId+"%");
		if(isLoginNameSet) {
			query.setParameter("loginName", "%"+loginName+"%");
		}
		return query.getSingleResult();
	}
}
