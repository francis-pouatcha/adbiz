package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkArt2SectionRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class StkArt2SectionLookup extends
		CoreAbstIdentifLookup<StkArt2Section> {

	@Inject
	private StkArt2SectionRepository repository;
	
	@Inject
	private StkSectionLookup stkSectionLookup;

	@Override
	protected CoreAbstIdentifRepo<StkArt2Section> getRepo() {
		return repository;
	}

	@Override
	protected Class<StkArt2Section> getEntityClass() {
		return StkArt2Section.class;
	}
	
	public String discoverSection(String artPic, String parentSection){
		if(StringUtils.isBlank(artPic)) return null;
		Long art2SectionCount = countByCntnrIdentif(artPic);
		if(art2SectionCount<=0) return null;
		if(StringUtils.isBlank(parentSection)){
			List<StkArt2Section> found = findByCntnrIdentif(artPic, 0, 1);
			if(!found.isEmpty()) return found.iterator().next().getSection();
		}
		
		int processed = 0;
		int max = 100;
		while(processed<art2SectionCount){
			int start = processed;
			processed+=max;
			List<StkArt2Section> found = findByCntnrIdentif(artPic, start, processed);
			for (StkArt2Section stkArt2Section : found) {
				StkSection stkSection = stkSectionLookup.findByIdentif(stkArt2Section.getSection());
				if(stkSection.isChildOf(parentSection)) return stkArt2Section.getSection();
			}
		}

		return null;
	}
}
