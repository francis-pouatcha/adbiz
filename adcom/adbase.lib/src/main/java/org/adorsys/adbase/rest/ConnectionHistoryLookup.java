package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.ConnectionHistorySearchInput;
import org.adorsys.adbase.repo.ConnectionHistoryRepository;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class ConnectionHistoryLookup extends CoreAbstIdentifLookup<ConnectionHistory>{

	@Inject
	private ConnectionHistoryRepository repository;
	
	@Inject
	private SecurityUtil securityUtil;
	
	

	@Override
	protected CoreAbstIdentifRepo<ConnectionHistory> getRepo() {
		return repository;
	}

	@Override
	protected Class<ConnectionHistory> getEntityClass() {
		return ConnectionHistory.class;
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
		TypedQuery<ConnectionHistory> query = getEntityManager().createQuery(qBuilder.toString(), ConnectionHistory.class);
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
		TypedQuery<Long> query = getEntityManager().createQuery(qBuilder.toString(), Long.class);
		query.setParameter("ouId", ouId+"%");
		if(isLoginNameSet) {
			query.setParameter("loginName", "%"+loginName+"%");
		}
		return query.getSingleResult();
	}}
