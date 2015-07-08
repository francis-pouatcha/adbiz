package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Description("CatalPicMapping_description")
public class CatalPicMapping extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 2679486739942764215L;

	@Column
	@Description("CatalPicMapping_artIdentif_description")
	@NotNull
	private String artIdentif;

	@Column
	@Description("CatalPicMapping_code_description")
	@NotNull
	private String code;

	/*
	 * This is the key of the enumeration from CatalCipOrigine
	 */
	@Column
	@Description("CatalPicMapping_codeOrigin_description")
	private String codeOrigin;

	/*
	 * Tite value of codeOrigin
	 */
	@Description("CatalPicMapping_codeOrigin_description.title")
	@Transient
	private String codeOriginTitle;

	/*
	 * Text value of codeOrigin
	 */
	@Transient
	@Description("CatalPicMapping_codeOrigin_description.text")
	private String codeOriginText;

	@Column
	@Description("CatalPicMapping_addInfo_description")
	private String addInfo;

	@Column
	@Description("CatalPicMapping_priority_description")
	private Integer priority;

	public String getArtIdentif() {
		return this.artIdentif;
	}

	public void setArtIdentif(final String artIdentif) {
		this.artIdentif = artIdentif;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getCodeOrigin() {
		return this.codeOrigin;
	}

	public void setCodeOrigin(final String codeOrigin) {
		this.codeOrigin = codeOrigin;
	}

	public String getAddInfo() {
		return this.addInfo;
	}

	public void setAddInfo(final String addInfo) {
		this.addInfo = addInfo;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(final Integer priority) {
		this.priority = priority;
	}

	@Override
	protected String makeIdentif() {
		return artIdentif;
	}

	public String getCodeOriginTitle() {
		return codeOriginTitle;
	}

	public void setCodeOriginTitle(String codeOriginTitle) {
		this.codeOriginTitle = codeOriginTitle;
	}

	public String getCodeOriginText() {
		return codeOriginText;
	}

	public void setCodeOriginText(String codeOriginText) {
		this.codeOriginText = codeOriginText;
	}

}