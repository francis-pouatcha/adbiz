package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrCtgryDtls_description")
public class BpPtnrCtgryDtls extends CoreAbstTimedData {

	private static final long serialVersionUID = -3436596869400887986L;

	@Column
	@Description("BpPtnrCtgryDtls_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpPtnrCtgryDtls_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("BpPtnrCtgryDtls_name_description")
	@NotNull
	private String name;

	@Column
	@Description("BpPtnrCtgryDtls_description_description")
	@Size(max = 256)
	private String description;

	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

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

	@Override
	protected String makeIdentif() {
		return toIdentif(ctgryCode, langIso2);
	}
	
	public static String toIdentif(String ctgryCode, String langIso2){
		return ctgryCode + "_" + langIso2;
	}
}