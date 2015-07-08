package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.repo.OuWorkspaceRepository;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adbase.util.OuWorkspaceId;

@Stateless
public class OuWorkspaceEJB 
{

	@Inject
	private OuWorkspaceRepository repository;

	@Inject
	private SecurityUtil secUtil;

	public OuWorkspace create(OuWorkspace entity)
	{
		return repository.save(attach(entity));
	}

	public OuWorkspace deleteById(String id)
	{
		OuWorkspace entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public OuWorkspace deleteCustomById(String id) {

		OuWorkspace entity = repository.findBy(id);
		if (entity != null)
		{
			entity.setValidTo(new Date());
			repository.save(entity);
		}
		return entity;
	}
	public OuWorkspace update(OuWorkspace entity)
	{
		return repository.save(attach(entity));
	}

	public OuWorkspace findById(String id)
	{
		return repository.findBy(id);
	}

	public List<OuWorkspace> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<OuWorkspace> findBy(OuWorkspace entity, int start, int max, SingularAttribute<OuWorkspace, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(OuWorkspace entity, SingularAttribute<OuWorkspace, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<OuWorkspace> findByLike(OuWorkspace entity, int start, int max, SingularAttribute<OuWorkspace, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(OuWorkspace entity, SingularAttribute<OuWorkspace, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private OuWorkspace attach(OuWorkspace entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
	public OuWorkspace findByIdentif(String identif, Date validOn){
		List<OuWorkspace> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}

	/**
	 * 	Assign a collection of OuWorkspace to an OrgUnit, represented here by its targetOutIdentif
	 * @param workspaces
	 * @param targetOuIdentif
	 * @return
	 */
	public List<OuWorkspace> assignOuWorkspaces(List<OuWorkspace> workspaces,String targetOuIdentif) {
		Date time = new Date();
		List<OuWorkspace> assignedWs = new LinkedList<>();
		for (OuWorkspace ouWorkspace : workspaces) {
			OuWorkspace assigned = assignOuWorkspace(ouWorkspace, targetOuIdentif, time);
			assignedWs.add(assigned);
		}
		return assignedWs;
	}
	/**
	 * Assign a new workspace to an OrgUnit, represented here by the targetOuIdentif
	 * @param workspace
	 * @param targetOuIdentif
	 * @param time
	 * @return
	 */
	public OuWorkspace assignOuWorkspace(OuWorkspace workspace,String targetOuIdentif, Date time) {
		String identif = workspace.getIdentif();
		OuWorkspaceId wsId = new OuWorkspaceId(identif);
		//test if the targetOu has a similiar actif workspace.
		boolean hasWorkspace = hasOuWorkspace(targetOuIdentif, wsId.getWsIdentif(), wsId.getOwnerOuIdentif(), time);
		if(hasWorkspace) {
			//delete the existing workspace
			OuWorkspace existingOuWs = findExstngOuWorkspaceForTarg(targetOuIdentif, wsId.getWsIdentif(), wsId.getOwnerOuIdentif(), time);
			return deleteCustomById(existingOuWs.getId());
		}else {
			//create a new ouworkspace
			OuWorkspace assignedWs = new OuWorkspace();
			assignedWs.setOwnerOuIdentif(wsId.getOwnerOuIdentif());
			assignedWs.setWsIdentif(wsId.getWsIdentif());
			assignedWs.setTargetOuIdentif(targetOuIdentif);
			return create(assignedWs);
		}
	}

	/**
	 *  Test if the targetOuIdentif has a workspace wsIdentif from this ownerOuIdentif at the date time
	 * @param targetOuIdentif
	 * @param wsIdentif
	 * @param ownerOuIdentif
	 * @param time
	 * @return
	 */
	public boolean hasOuWorkspace(String targetOuIdentif,String wsIdentif,String ownerOuIdentif,Date time) {
		OuWorkspace ouWorkspace = findExstngOuWorkspaceForTarg(targetOuIdentif, wsIdentif, ownerOuIdentif, time);
		return ouWorkspace != null;
	}
	private OuWorkspace findExstngOuWorkspaceForTarg(String targetOuIdentif,String wsIdentif,String ownerOuIdentif,Date time) {
		String identif = new OuWorkspaceId(ownerOuIdentif, wsIdentif, targetOuIdentif).getIdentif();
		OuWorkspace ouWorkspace = findByIdentif(identif, time);
		return ouWorkspace;
	}
	public List<OuWorkspace> findByTargetOuIdentif(String targetOuIdentif, Date validOn) {
		
		return repository.findByTargetOuIdentif(targetOuIdentif, validOn);
	}
	public List<OuWorkspace> findActivesOuWorkspaces() {
		String identif = secUtil.getCurrentOrgUnit().getIdentif();
		return findActivesOuWorkspaces(identif);
	}

	public List<OuWorkspace> findActivesOuWorkspaces(String identif) {
		return repository.findByTargetOuIdentif(identif, new Date());
	}


}
