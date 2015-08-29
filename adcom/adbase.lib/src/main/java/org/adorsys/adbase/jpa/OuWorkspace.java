package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseOuWorkspace")
@Description("OuWorkspace_description")
public class OuWorkspace extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 5003489119950388983L;

	@Column
	@Description("OuWorkspace_ownerOuIdentif_description")
	@NotNull
	private String ownerOuIdentif;

	@Column
	@Description("OuWorkspace_wsIdentif_description")
	@NotNull
	private String wsIdentif;

	@Column
	@Description("OuWorkspace_targetOuIdentif_description")
	@NotNull
	private String targetOuIdentif;

	public OuWorkspace() {
		super();
	}

	public OuWorkspace(String ownerOuIdentif, String wsIdentif,
			String targetOuIdentif) {
		super();
		this.ownerOuIdentif = ownerOuIdentif;
		this.wsIdentif = wsIdentif;
		this.targetOuIdentif = targetOuIdentif;
	}

	public String getOwnerOuIdentif() {
		return this.ownerOuIdentif;
	}

	public void setOwnerOuIdentif(final String ownerOuIdentif) {
		this.ownerOuIdentif = ownerOuIdentif;
	}

	public String getWsIdentif() {
		return this.wsIdentif;
	}

	public void setWsIdentif(final String wsIdentif) {
		this.wsIdentif = wsIdentif;
	}

	public String getTargetOuIdentif() {
		return this.targetOuIdentif;
	}

	public void setTargetOuIdentif(final String targetOuIdentif) {
		this.targetOuIdentif = targetOuIdentif;
	}

	@Override
	protected String makeIdentif() {
		return ownerOuIdentif + "_" + wsIdentif + "_" + targetOuIdentif;
	}

	public static OuWorkspace toOuWorkspace(String identif){
		String[] split = StringUtils.split(identif, "_");
		if(split.length<3) return null;
		return new OuWorkspace(split[0], split[1], split[2]);
	}
}