package org.adorsys.adbase.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.holder.UserWorkspaceHolder;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.LoginConfiguration;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.repo.UserWorkspaceRepository;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class UserWorkspaceEJB {

	@Inject
	private UserWorkspaceRepository repository;
	
	@Inject
	private OuWorkspaceEJB ouWorkspaceEJB;
	
	@Inject
	private WorkspaceEJB workspaceEJB;
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private LoginEJB loginEJB;
	
	@Inject
	private LoginConfigurationEJB loginConfigEjb;

	public UserWorkspace create(UserWorkspace entity) {
		return repository.save(attach(entity));
	}

	public UserWorkspace deleteById(String id) {
		UserWorkspace entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public UserWorkspace update(UserWorkspace entity) {
		return repository.save(attach(entity));
	}

	public UserWorkspace findById(String id) {
		return repository.findBy(id);
	}

	public List<UserWorkspace> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<UserWorkspace> findBy(UserWorkspace entity, int start, int max,
			SingularAttribute<UserWorkspace, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(UserWorkspace entity,
			SingularAttribute<UserWorkspace, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<UserWorkspace> findByLike(UserWorkspace entity, int start,
			int max, SingularAttribute<UserWorkspace, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(UserWorkspace entity,
			SingularAttribute<UserWorkspace, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private UserWorkspace attach(UserWorkspace entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public UserWorkspace findByIdentif(String identif, Date validOn) {
		List<UserWorkspace> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<UserWorkspace> findUserWorkspaces() {
		String loginName = securityUtil.getCurrentLoginName();
		return repository.findByLoginName(loginName, new Date()).getResultList();
	}

	public UserWorkspaceHolder wsin() {
		Date now = new Date();
		TermWsUserPrincipal principal = securityUtil.getCallerPrincipal();
		String workspaceId = principal.getWorkspaceId();
		if(Login.loginWorkspace(workspaceId)){
			return loginWorkspace(principal);
		}
		List<UserWorkspace> resultList = repository.findByIdentif(workspaceId, now).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		UserWorkspace userWs = resultList.iterator().next();
		String loginName = securityUtil.getCurrentLoginName();
		if(!StringUtils.equals(userWs.getLoginName(), loginName)) return null;
		String ouWsIdentif = userWs.getOuWsIdentif();
		OuWorkspace ouWorkspace = ouWorkspaceEJB.findByIdentif(ouWsIdentif, now);
		if(ouWorkspace==null) return null;
		String wsIdentif = ouWorkspace.getWsIdentif();
		Workspace workspace = workspaceEJB.findByIdentif(wsIdentif, now);
		if(workspace==null) return null;
		
		UserWorkspaceHolder holder = new UserWorkspaceHolder();
		holder.setClientApp(workspace.getClientApp());
		holder.setLoginName(loginName);
		holder.setOuTypes(workspace.getOuTypes());
		holder.setRoleIdentif(workspace.getRoleIdentif());
		holder.setTargetOuIdentif(ouWorkspace.getTargetOuIdentif());
		Login login = loginEJB.findById(loginName);
		holder.setUserFullName(login.getFullName());
		holder.setEmail(login.getEmail());
		holder.setTerminalName(principal.getTermName());
		holder.setTimeZone(principal.getTimeZone());
		holder.setMaxRebate(loginMaxRebate());
		if(StringUtils.isNotBlank(principal.getLangIso2())){
			holder.setLangIso2(principal.getLangIso2());
		} else {
			holder.setLangIso2(login.getLangIso2());
		}
		return holder;
	}
	
	private UserWorkspaceHolder loginWorkspace(TermWsUserPrincipal principal){
		Login login = loginEJB.findById(principal.getLoginName());
		UserWorkspaceHolder holder = new UserWorkspaceHolder();
		holder.setClientApp("/adlogin.client");
		holder.setLoginName(login.getLoginName());
		if(StringUtils.isNotBlank(principal.getLangIso2())){
			holder.setLangIso2(principal.getLangIso2());
		} else {
			holder.setLangIso2(login.getLangIso2());
		}
		holder.setOuTypes(null);
		holder.setRoleIdentif("login");
		holder.setTargetOuIdentif(login.getOuIdentif());
		holder.setUserFullName(login.getFullName());
		holder.setEmail(login.getEmail());
		holder.setTerminalName(principal.getTermName());
		holder.setTimeZone(principal.getTimeZone());
		holder.setMaxRebate(loginMaxRebate());
		return holder;
	}
	
	public String wsout(String identif) {
		if(Login.loginWorkspace(identif)) return "/adlogin.client";
		Date now = new Date();
		List<UserWorkspace> resultList = repository.findByIdentif(identif, now).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		UserWorkspace userWs = resultList.iterator().next();
		String loginName = securityUtil.getCurrentLoginName();
		if(!StringUtils.equals(userWs.getLoginName(), loginName)) return null;
		String ouWsIdentif = userWs.getOuWsIdentif();
		OuWorkspace ouWorkspace = ouWorkspaceEJB.findByIdentif(ouWsIdentif, now);
		if(ouWorkspace==null) return null;
		String wsIdentif = ouWorkspace.getWsIdentif();
		Workspace workspace = workspaceEJB.findByIdentif(wsIdentif, now);
		if(workspace==null) return null;
		return workspace.getClientApp();
	}
	
	public List<UserWorkspace> findAssignedUserWorkspace(String loginName, String ouWsIdentif){
		return repository.findByLoginNameAndOuWsIdentif(loginName, ouWsIdentif, new Date()).getResultList();
	}
	
	private BigDecimal loginMaxRebate(){
		String loginName = securityUtil.getCurrentLoginName();
		List<LoginConfiguration> loginConfig = loginConfigEjb.findByLogin(loginName);
		if(loginConfig.isEmpty())
			return null;
		return loginConfig.get(0).getMaxRebate();
	}
	
}
