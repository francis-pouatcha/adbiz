/**
 * 
 */
package org.adorsys.adbase.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
public class OuWorkspaceId {
	
	private String identif;
	private String ownerOuIdentif;
	private String wsIdentif;
	private String targetOuIdentif;
	
	
	public OuWorkspaceId(String identif) {
		this.identif = identif;
		setReferenceIdentifs(identif);
	}

	
	public OuWorkspaceId(String ownerOuIdentif, String wsIdentif,
			String targetOuIdentif) {
		super();
		this.ownerOuIdentif = ownerOuIdentif;
		this.wsIdentif = wsIdentif;
		this.targetOuIdentif = targetOuIdentif;
		this.identif = extracted(ownerOuIdentif, wsIdentif, targetOuIdentif);
	}


	private String extracted(String ownerOuIdentif, String wsIdentif, String targetOuIdentif) {
		return ownerOuIdentif + "_" + wsIdentif + "_" + targetOuIdentif;
	}
	
	private String[] splitIdentif(String identif) {
		String[] parts = StringUtils.split(identif, "_");
		if(parts.length != 3) {
			throw new IllegalStateException("Unable to split the WorkspaceId");
		}
		return parts;
	}
	
	private void setReferenceIdentifs(String identif) {
		String[] parts = splitIdentif(identif);
		this.ownerOuIdentif = parts[0];
		this.wsIdentif = parts[1];
		this.targetOuIdentif = parts[2];
	}


	public String getIdentif() {
		return identif;
	}


	public String getOwnerOuIdentif() {
		return ownerOuIdentif;
	}


	public String getTargetOuIdentif() {
		return targetOuIdentif;
	}
	

	public String getWsIdentif() {
		return wsIdentif;
	}


	@Override
	public String toString() {
		return "OuWorkspaceId [identif=" + identif + ", ownerOuIdentif="
				+ ownerOuIdentif + ", wsIdentif=" + wsIdentif
				+ ", targetOuIdentif=" + targetOuIdentif + "]";
	}
	
}
