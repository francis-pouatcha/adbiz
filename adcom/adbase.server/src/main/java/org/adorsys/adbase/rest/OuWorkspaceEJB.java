package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.repo.OuWorkspaceRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class OuWorkspaceEJB extends CoreAbstIdentifiedEJB<OuWorkspace>
{

	@Inject
	private OuWorkspaceRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OuWorkspace> getRepo() {
		return repository;
	}

//	@Inject
//	private SecurityUtil secUtil;

	/**
	 * 	Assign a collection of OuWorkspace to an OrgUnit, represented here by its targetOutIdentif
	 * @param workspaces
	 * @param targetOuIdentif
	 * @return
	public List<OuWorkspace> assignOuWorkspaces(List<OuWorkspace> workspaces,String targetOuIdentif) {
		Date time = new Date();
		List<OuWorkspace> assignedWs = new LinkedList<>();
		for (OuWorkspace ouWorkspace : workspaces) {
			OuWorkspace assigned = assignOuWorkspace(ouWorkspace, targetOuIdentif, time);
			assignedWs.add(assigned);
		}
		return assignedWs;
	}
	 */
	/**
	 * Assign a new workspace to an OrgUnit, represented here by the targetOuIdentif
	 * @param workspace
	 * @param targetOuIdentif
	 * @param time
	 * @return
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
	 */


}
