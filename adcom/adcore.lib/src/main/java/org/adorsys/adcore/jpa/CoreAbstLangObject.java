package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * This is an entity with a business key. The business key is an information
 * we can use to uniquely retries the object.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstLangObject extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -147563632588553265L;

	@Column
	@NotNull
	private String langIso2;

	public String getLangIso2() {
		return langIso2;
	}

	public void setLangIso2(String langIso2) {
		this.langIso2 = langIso2;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(getCntnrIdentif(), getLangIso2());
	}

	public static String toIdentif(String cntnrIdentif, String langIso2) {
		return cntnrIdentif + "_" + langIso2;
	}
}