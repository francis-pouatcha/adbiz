package org.adorsys.adbase.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.exception.NotFoundOrNotActifEntityException;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OrgUnitId;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.rest.OrgUnitIdEJB;
import org.adorsys.adbase.rest.OuWorkspaceEJB;
import org.adorsys.adbase.security.SecurityUtil;

@Singleton
public class OuWorkspaceDtoService {
	
	@Inject
	private OuWorkspaceEJB ouWorkspaceEjb;
	
	@Inject
	private OrgUnitDtoService orgUnitDtoService;
	
	@Inject
	private SecurityUtil secUtil;
	
	@Inject
	private OrgUnitIdEJB ouIdEJB;
	
	/**
	 * Create a list of OuWorkspace, with a boolean indicating whether the workspace has been assigned to the targetOuIdentif.
	 * @param targetOuIdentif
	 * @return
	 * @throws NotFoundOrNotActifEntityException 
	 */
	public OuWorkspaceDTOHolder createDtos(String targetOuIdentif) throws NotFoundOrNotActifEntityException {
		Date time = new Date();
		OrgUnitId orgUnitId = ouIdEJB.toOrgUnitId(targetOuIdentif);
		String parentId = orgUnitId.getParentId()!=null?orgUnitId.getParentId().getOrgUnit().getIdentif():secUtil.getCurrentOrgUnit().getIdentif();
		List<OuWorkspace> findActivParentOuWorkspaces = ouWorkspaceEjb.findActivesOuWorkspaces(parentId);
		List<OuWorkspaceDTO> dtos = new ArrayList<OuWorkspaceDTO>();
		for (OuWorkspace ouWorkspace : findActivParentOuWorkspaces) {
			OuWorkspaceDTO dto = createDto(targetOuIdentif, ouWorkspace,time);
			dtos.add(dto);
		}
		OrgUnitDto targetOu = orgUnitDtoService.convertOrgUnit(targetOuIdentif);
		OrgUnit currentOrgUnit = secUtil.getCurrentOrgUnit();
		OrgUnitDto currOu = orgUnitDtoService.convertOrgUnit(currentOrgUnit);
		
		OuWorkspaceDTOHolder dtoHolder = new OuWorkspaceDTOHolder(currOu, targetOu, dtos);
		return dtoHolder;
	}
	/**
	 * @param targetOuIdentif
	 * @param workspace
	 * @param time
	 * @return
	 */
	public OuWorkspaceDTO createDto(String targetOuIdentif,OuWorkspace workspace,Date time) {
		boolean hasOuWorkspace = ouWorkspaceEjb.hasOuWorkspace(targetOuIdentif, workspace.getWsIdentif(), workspace.getOwnerOuIdentif(), time);
		return new OuWorkspaceDTO(workspace, hasOuWorkspace);
	}
	
	public boolean assignWorkspaces(OuWorkspaceDTOHolder dtoHolder) {
		List<OuWorkspaceDTO> dtos = dtoHolder.getDtos();
		Date time = new Date();
		for (OuWorkspaceDTO ouWorkspaceDTO : dtos) {
			OuWorkspace workspace = ouWorkspaceEjb.findByIdentif(ouWorkspaceDTO.getIdentif(), time);
			ouWorkspaceEjb.assignOuWorkspace(workspace, dtoHolder.getTargetOu().getIdentif(), time);
		}
		return true;
	}
}
