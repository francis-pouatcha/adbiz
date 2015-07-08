package org.adorsys.adbase.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adbase.dto.LoginWorkspaceDto;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.jpa.Workspace;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class LoginWsDtoService {

	@Inject
	private OuWorkspaceEJB ouWorkspaceEJB;
	@Inject
	private UserWorkspaceEJB userWorkspaceEJB;
	@Inject
	private WorkspaceEJB workspaceEJB;
		
	public  List<LoginWorkspaceDto> load(String orgUnit, String loginName) {
		
		List<OuWorkspace> listOuWs = ouWorkspaceEJB.findByTargetOuIdentif(orgUnit, new Date());
		List<LoginWorkspaceDto> listWsDto = new ArrayList<LoginWorkspaceDto>();
		for(OuWorkspace ouWs:listOuWs){
			LoginWorkspaceDto workspaceDto = createWorkspaceDto(ouWs,loginName);
			List<UserWorkspace> listAssignedUserWs = userWorkspaceEJB.findAssignedUserWorkspace(loginName, ouWs.getIdentif());
			if(listAssignedUserWs.iterator().hasNext()){
				workspaceDto = updateWorkspaceDto(workspaceDto,listAssignedUserWs.iterator().next());
			}
			listWsDto.add(workspaceDto);
		}
		
		return listWsDto;
	}

	private LoginWorkspaceDto updateWorkspaceDto(LoginWorkspaceDto workspaceDto,
			UserWorkspace userWs) {
			
		workspaceDto.setUserWsIdentif(userWs.getIdentif());
		workspaceDto.setAssigned(true);
		return workspaceDto;
	}

	private LoginWorkspaceDto createWorkspaceDto(OuWorkspace ouWs, String loginName) {
		
		LoginWorkspaceDto workspaceDto = new LoginWorkspaceDto();
		workspaceDto.setLoginName(loginName);
		workspaceDto.setOuWsIdentif(ouWs.getIdentif());
		workspaceDto.setOuIdentif(ouWs.getTargetOuIdentif());
		workspaceDto.setWsIdentif(ouWs.getWsIdentif());
		
		Workspace workspace = workspaceEJB.findByIdentif(ouWs.getWsIdentif(), new Date());
		workspaceDto.setRoleIdentif(workspace.getRoleIdentif());
		workspaceDto.setClientApp(workspace.getClientApp());
		workspaceDto.setOuTypeName(workspace.getOuTypes());
		
		return workspaceDto;
	}

	public List<LoginWorkspaceDto> saveUserWorkspace(
			List<LoginWorkspaceDto> loginWorkspaceDtos) {
		
		for(LoginWorkspaceDto wsDto:loginWorkspaceDtos){
			if(wsDto.getAssigned()){
				if(StringUtils.isBlank(wsDto.getUserWsIdentif())){
					UserWorkspace userWorkspace = new UserWorkspace(wsDto.getLoginName(), wsDto.getOuWsIdentif());
					userWorkspaceEJB.create(userWorkspace);
				}
			}else{// not assigned
					if(StringUtils.isNotBlank(wsDto.getUserWsIdentif())){
						UserWorkspace userWorkspace = userWorkspaceEJB.findByIdentif(wsDto.getUserWsIdentif(), new Date());
						userWorkspace.setValidTo(new Date());
						userWorkspaceEJB.update(userWorkspace);
					}
				
			}
		}
		if(loginWorkspaceDtos.iterator().hasNext()){
			LoginWorkspaceDto next = loginWorkspaceDtos.iterator().next();
			String loginName = next.getLoginName();
			String orgUnit = next.getOuIdentif();
			return load(orgUnit, loginName);
		}
		return null;
	}

}
