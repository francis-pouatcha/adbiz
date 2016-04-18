package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstLangObject;

/**
 * 
 * @author fpo
 *
 */
@Entity
@Description("BpPtnrCtgryDtls_description")
public class BpPtnrCtgryDtls extends CoreAbstLangObject {

	private static final long serialVersionUID = -3436596869400887986L;

	@Column
	@Description("BpPtnrCtgryDtls_name_description")
	@NotNull
	private String name;

	@Column
	@Description("BpPtnrCtgryDtls_description_description")
	@Size(max = 256)
	private String description;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}