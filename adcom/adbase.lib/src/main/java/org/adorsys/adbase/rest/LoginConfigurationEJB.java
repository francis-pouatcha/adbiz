package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.LoginConfiguration;
import org.adorsys.adbase.repo.LoginConfigurationRepository;

@Stateless
public class LoginConfigurationEJB {

	@Inject
	private LoginConfigurationRepository repository;

	public LoginConfiguration create(LoginConfiguration entity) {

		entity.setIdentif(entity.getLoginName());
		entity.setId(entity.getLoginName());
		return repository.save(attach(entity));
	}

	public LoginConfiguration deleteById(String id) {
		LoginConfiguration entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public LoginConfiguration update(LoginConfiguration entity) {
		return repository.save(attach(entity));
	}

	public LoginConfiguration findById(String id) {
		return repository.findBy(id);
	}

	public List<LoginConfiguration> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<LoginConfiguration> findBy(LoginConfiguration entity, int start, int max,
			SingularAttribute<LoginConfiguration, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(LoginConfiguration entity,
			SingularAttribute<LoginConfiguration, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<LoginConfiguration> findByLike(LoginConfiguration entity, int start, int max,
			SingularAttribute<LoginConfiguration, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(LoginConfiguration entity,
			SingularAttribute<LoginConfiguration, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private LoginConfiguration attach(LoginConfiguration entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public LoginConfiguration findByIdentif(String identif, Date validOn) {
		List<LoginConfiguration> resultList = repository.findByIdentif(identif)
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<LoginConfiguration> findPreviousLogin(String identif) {
		return repository.findPreviousLoginRebate(identif).maxResults(2)
				.getResultList();
	}

	public List<LoginConfiguration> findNextLogin(String identif) {
		return repository.findNextLoginRebate(identif).maxResults(2)
				.getResultList();
	}
	
	 public List<LoginConfiguration> findByLogin(String loginName){
			return repository.findByLogin(loginName).getResultList();
		}

}
