package org.adorsys.adbase.dto;

import org.adorsys.adbase.jpa.OuWorkspace;


public class OuWorkspaceDTO {

	private String identif;
	
	private String ownerOuIdentif;

	private String wsIdentif;

	private String targetOuIdentif;
	
	private boolean assigned;

	public OuWorkspaceDTO() {}
	
	
	public OuWorkspaceDTO(String identif, String ownerOuIdentif,
			String wsIdentif, String targetOuIdentif, boolean assigned) {
		super();
		this.identif = identif;
		this.ownerOuIdentif = ownerOuIdentif;
		this.wsIdentif = wsIdentif;
		this.targetOuIdentif = targetOuIdentif;
		this.assigned = assigned;
	}
	
	public OuWorkspaceDTO(OuWorkspace ouWorkspace,boolean assinged) {
		this(ouWorkspace.getIdentif(), ouWorkspace.getOwnerOuIdentif(), ouWorkspace.getWsIdentif(), ouWorkspace.getTargetOuIdentif(), assinged);
	}

	public String getIdentif() {
		return identif;
	}

	public void setIdentif(String identif) {
		this.identif = identif;
	}

	public String getOwnerOuIdentif() {
		return ownerOuIdentif;
	}

	public void setOwnerOuIdentif(String ownerOuIdentif) {
		this.ownerOuIdentif = ownerOuIdentif;
	}

	public String getWsIdentif() {
		return wsIdentif;
	}

	public void setWsIdentif(String wsIdentif) {
		this.wsIdentif = wsIdentif;
	}

	public String getTargetOuIdentif() {
		return targetOuIdentif;
	}

	public void setTargetOuIdentif(String targetOuIdentif) {
		this.targetOuIdentif = targetOuIdentif;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
	
	@Override
	public String toString() {
		return "OuWorkspaceDTO [identif=" + identif + ", ownerOuIdentif="
				+ ownerOuIdentif + ", wsIdentif=" + wsIdentif
				+ ", targetOuIdentif=" + targetOuIdentif + ", assigned="
				+ assigned + "]";
	}
	
}
