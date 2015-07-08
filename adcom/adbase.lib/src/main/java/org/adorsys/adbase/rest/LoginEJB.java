package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.repo.LoginRepository;

@Stateless
public class LoginEJB {

	@Inject
	private LoginRepository repository;
	
	public Login create(Login entity) {
		
		entity.setIdentif(entity.getLoginName());
		return repository.save(attach(entity));
	}

	public Login deleteById(String id) {
		Login entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public Login update(Login entity) {
		return repository.save(attach(entity));
	}

	public Login findById(String id) {
		return repository.findBy(id);
	}

	public List<Login> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<Login> findBy(Login entity, int start, int max,
			SingularAttribute<Login, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(Login entity, SingularAttribute<Login, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<Login> findByLike(Login entity, int start, int max,
			SingularAttribute<Login, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(Login entity,
			SingularAttribute<Login, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private Login attach(Login entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public Login findByIdentif(String identif, Date validOn) {
		List<Login> resultList = repository.findByIdentif(identif)
			.maxResults(1).getResultList();
		if (resultList.isEmpty())return null;
		return resultList.iterator().next();
	}

	public Login findByLoginAlias(String loginAlias) {
		List<Login> resultList = repository.findByLoginAlias(loginAlias).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	
	public List<Login> findPreviousLogin(String loginName){
		return repository.findPreviousLogin(loginName).maxResults(2).getResultList();
	}
	
	public List<Login> findNextLogin(String loginName){
		return repository.findNextLogin(loginName).maxResults(2).getResultList();
	}
	
	
}
