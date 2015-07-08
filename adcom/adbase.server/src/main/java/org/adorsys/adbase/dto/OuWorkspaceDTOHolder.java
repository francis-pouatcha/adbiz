/**
 * 
 */
package org.adorsys.adbase.dto;

import java.util.List;

/**
 * @author boriswaguia
 *
 */
public class OuWorkspaceDTOHolder {
	
	private OrgUnitDto currOu;
	private OrgUnitDto targetOu;
	private List<OuWorkspaceDTO> dtos;
	
	public OuWorkspaceDTOHolder() {	}

	public OuWorkspaceDTOHolder(OrgUnitDto currOu, OrgUnitDto targetOu,
			List<OuWorkspaceDTO> dtos) {
		super();
		this.currOu = currOu;
		this.targetOu = targetOu;
		this.dtos = dtos;
	}


	public OrgUnitDto getCurrOu() {
		return currOu;
	}

	public void setCurrOu(OrgUnitDto currOu) {
		this.currOu = currOu;
	}

	public OrgUnitDto getTargetOu() {
		return targetOu;
	}

	public void setTargetOu(OrgUnitDto targetOu) {
		this.targetOu = targetOu;
	}

	public List<OuWorkspaceDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<OuWorkspaceDTO> dtos) {
		this.dtos = dtos;
	}

	@Override
	public String toString() {
		return "OuWorkspaceDTOHolder [currOu=" + currOu + ", targetOu="
				+ targetOu + ", dtos=" + dtos + "]";
	}
	
}
