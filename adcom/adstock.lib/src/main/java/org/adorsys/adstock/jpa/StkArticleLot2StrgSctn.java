package org.adorsys.adstock.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

/**
 * Mapping of the location of an article lot.
 * 
 * @author fpo
 *
 */
@Entity
@Description("StkArticleLot2StrgSctn_description")
public class StkArticleLot2StrgSctn extends StkAbstLot2Section {
	private static final long serialVersionUID = 3908681222499908094L;

	@Override
	protected String makeIdentif() {
		return toLotPicAndSectionKey(getLotPic(), getSection());
	}
}