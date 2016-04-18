package org.adorsys.adcatal.jpa;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
public class CatalArticleSearchInput extends CoreAbstIdentifObjectSearchInput<CatalArticle>{
	private String artName;
	
	private String prodFmly;

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public String getProdFmly() {
		return prodFmly;
	}

	public void setProdFmly(String prodFmly) {
		this.prodFmly = prodFmly;
	}
}
