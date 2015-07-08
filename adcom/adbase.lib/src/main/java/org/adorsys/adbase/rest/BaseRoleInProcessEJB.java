package org.adorsys.adbase.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseRoleInProcess;
import org.adorsys.adbase.repo.BaseRoleInProcessRepository;
import org.adorsys.adbase.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class BaseRoleInProcessEJB {
	@Inject
	private BaseRoleInProcessRepository repository;
	@Inject
	private SecurityUtil securityUtil;

	public BaseRoleInProcess create(BaseRoleInProcess entity) {
		return repository.save(attach(entity));
	}

	public BaseRoleInProcess deleteById(String id) {
		BaseRoleInProcess entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseRoleInProcess update(BaseRoleInProcess entity) {
		return repository.save(attach(entity));
	}

	public BaseRoleInProcess findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseRoleInProcess> listAll(int start, int max) {
		return processI18n(repository.findAll(start, max));
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseRoleInProcess> findBy(BaseRoleInProcess entity, int start,
			int max, SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseRoleInProcess entity,
			SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseRoleInProcess> findByLike(BaseRoleInProcess entity, int start,
			int max, SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseRoleInProcess entity,
			SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseRoleInProcess attach(BaseRoleInProcess entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseRoleInProcess findByIdentif(String identif) {
		return findById(identif);
	}
	
	private List<BaseRoleInProcess> processI18n(List<BaseRoleInProcess> BaseRoleInProcesss){
		String langIso2 = securityUtil.getUserLange();
		List<String> listLangIso2 = securityUtil.getUserLangePrefs();
		if(StringUtils.isBlank(langIso2)){
			langIso2 = listLangIso2.get(0);
		}	
		List<BaseRoleInProcess> listTriggerI18n = new ArrayList<BaseRoleInProcess>();
		for(BaseRoleInProcess trigger:BaseRoleInProcesss){
			if(StringUtils.equals(langIso2,trigger.getLangIso2())){
				listTriggerI18n.add(trigger);
			}
		}
		return listTriggerI18n;
	}
}
