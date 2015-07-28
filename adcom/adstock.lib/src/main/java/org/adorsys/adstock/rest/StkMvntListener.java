package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.repo.StkMvntRepository;

@Stateless
public class StkMvntListener {

	@Inject
	private StkMvntRepository repository;

	public StkMvnt fireNewMvntEvent(StkMvnt stkMvnt){
		return repository.save(stkMvnt);
	}
	
}
