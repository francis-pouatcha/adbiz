package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity 
@Table(name="BaseWorkspace")
@Description("Workspace_description")
public class Workspace extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 6506646901353587934L;

	@Column
	@Description("Workspace_roleIdentif_description")
	@NotNull
	private String roleIdentif;

	@Column
	@Description("Workspace_ouTypes_description")
	private String ouTypes;

	@Column
	@Description("Workspace_clientApp_description")
	private String clientApp;
	
	public String getRoleIdentif() {
		return this.roleIdentif;
	}

	public void setRoleIdentif(final String roleIdentif) {
		this.roleIdentif = roleIdentif;
	}

	public String getOuTypes() {
		return this.ouTypes;
	}

	public void setOuTypes(final String ouTypes) {
		this.ouTypes = ouTypes;
	}
	
	public String getClientApp() {
		return clientApp;
	}

	public void setClientApp(String clientApp) {
		this.clientApp = clientApp;
	}

	@Override
	protected String makeIdentif() {
		return roleIdentif;
	}
}