package org.adorsys.adstock.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class StkArticleLotSearchInput extends CoreAbstBsnsItemSearchInput<StkArticleLot>{

	private boolean withStrgSection;

	private String sectionCode;

	/**
	 * The articles pics. if filled, it means the user want articleslot for all theses pics.
	 */
	private List<String> artPics;

	public boolean isWithStrgSection() {
		return withStrgSection;
	}

	public void setWithStrgSection(boolean withStrgSection) {
		this.withStrgSection = withStrgSection;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public List<String> getArtPics() {
		return artPics;
	}

	public void setArtPics(List<String> artPics) {
		this.artPics = artPics;
	}

	/**
	 * isFindByNameRange.
	 *
	 * @return
	 */
	public boolean isFindByNameRange() {
		return artPics != null && !artPics.isEmpty();
	}
}
