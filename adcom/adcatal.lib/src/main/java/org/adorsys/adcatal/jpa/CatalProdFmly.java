package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.utils.SequenceGenerator;

@Entity
@Description("CatalProductFamily_description")
public class CatalProdFmly extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -3125003867184463811L;

	@Column
	@Description("CatalProductFamily_parentIdentif_description")
	private String parentIdentif;

	/*
	 * This is the mapping of the path from the root to the immediate parent of
	 * this product. We will use slash as a path separator.
	 */
	@Column
	@Description("CatalProductFamily_famPath_description")
	private String famPath;

	public String getParentIdentif() {
		return this.parentIdentif;
	}

	public void setParentIdentif(final String parentIdentif) {
		this.parentIdentif = parentIdentif;
	}

	@Override
	protected String makeIdentif() {
		return SequenceGenerator.getSequence(SequenceGenerator.PRODUCT_FAMILY_SEQUENCE_PREFIXE);
	}

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}
}