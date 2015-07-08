package org.adorsys.adstock.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkArticleLotRepository;
import org.apache.commons.lang3.StringUtils;

/**
 * Not an ejb.
 * 
 * @author francis
 *
 */
public class StkStrgSectionDetachHelper {

	@Inject
	private StkArticleLotRepository repository;

	@Inject
	private StkSectionLookup stkSectionLookup;

	public StkArticleLot2StrgSctn detach(StkArticleLot2StrgSctn entity) {
		if (entity == null)
			return null;

		String strgSection = entity.getStrgSection();
		if (StringUtils.isNotBlank(strgSection)) {
			StkSection stkSection = stkSectionLookup.findByIdentif(strgSection,
					new Date());
			if (stkSection != null)
				entity.setStkSection(stkSection);
		}
		StkArticleLot stkArticleLot = findByIdentif(StkArticleLot.toId(entity.getLotPic()));
		entity.setSectionArticleLot(stkArticleLot);
		return entity;
	}

	public List<StkArticleLot2StrgSctn> detachStrSections(
			List<StkArticleLot2StrgSctn> list) {
		if (list == null)
			return list;
		List<StkArticleLot2StrgSctn> result = new ArrayList<StkArticleLot2StrgSctn>();
		for (StkArticleLot2StrgSctn entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	public StkArticleLot findByIdentif(String identif) {
		return repository.findOptionalByIdentif(identif);
	}
}
