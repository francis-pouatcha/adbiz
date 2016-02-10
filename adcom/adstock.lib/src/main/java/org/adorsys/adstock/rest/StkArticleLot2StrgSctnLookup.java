package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StkArticleLot2StrgSctnLookup extends
		CoreAbstIdentifLookup<StkArticleLot2StrgSctn> {

	@Inject
	private StkArticleLot2StrgSctnRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArticleLot2StrgSctn> getRepo() {
		return repository;
	}

	public StkArticleLot2StrgSctn findBySectionAndLotPic(String strgSection, String lotPic){
		String primaryKey = StkArticleLot2StrgSctn.toLotPicAndSectionKey(lotPic, strgSection);
		return repository.findBy(primaryKey);
	}

	@Override
	protected Class<StkArticleLot2StrgSctn> getEntityClass() {
		return StkArticleLot2StrgSctn.class;
	}
	
	public Long countByClosedDtIsNull(){
		return repository.findByClosedDtIsNull().count();
	}
	public List<StkArticleLot2StrgSctn> findByClosedDtIsNullAsc(int start, int max){
		return repository.findByClosedDtIsNull().firstResult(start).maxResults(max).orderAsc("identif").getResultList();
	}
	public List<StkArticleLot2StrgSctn> findByClosedDtIsNullDesc(int start, int max){
		return repository.findByClosedDtIsNull().firstResult(start).maxResults(max).orderDesc("identif").getResultList();
	}

	public Long countBySectionAndClosedDtIsNull(String section){
		return repository.findBySectionAndClosedDtIsNull(section).count();
	}
	public List<StkArticleLot2StrgSctn> findBySectionAndClosedDtIsNullAsc(String section, int start, int max){
		return repository.findBySectionAndClosedDtIsNull(section).firstResult(start).maxResults(max).orderAsc("identif").getResultList();
	}
	public List<StkArticleLot2StrgSctn> findBySectionAndClosedDtIsNullDesc(String section, int start, int max){
		return repository.findBySectionAndClosedDtIsNull(section).firstResult(start).maxResults(max).orderDesc("identif").getResultList();
	}

	public Long countByArtPicAndSection(String artPic, String section){
		return repository.findByArtPicAndSection(artPic, section).count();
	}
	public List<StkArticleLot2StrgSctn> findByArtPicAndSection(String artPic, String section, int start, int max){
		return repository.findByArtPicAndSection(artPic, section).firstResult(start).maxResults(max).orderAsc("identif").getResultList();
	}

	public Long countByArtPic(String artPic){
		return repository.findByArtPic(artPic).count();
	}
	public List<StkArticleLot2StrgSctn> findByArtPic(String artPic, int start, int max){
		return repository.findByArtPic(artPic).firstResult(start).maxResults(max).orderAsc("identif").getResultList();
	}
	
	public Long countByArtPicAndExpitDt(String artPic, Date expirDt) {
		return repository.findByArtPicAndExpitDt(artPic, expirDt).count();
	}
	public List<StkArticleLot2StrgSctn> findByArtPicAndExpitDt(String artPic, Date expirDt, int start, int max) {
		return repository.findByArtPicAndExpitDt(artPic, expirDt).firstResult(start).maxResults(max).getResultList();
	}
	
	@Inject
	private StkSectionLookup stkSectionLookup;

	/**
	 * Tries to discover a record of this article
	 * 
	 * @param artPic
	 * @param section
	 * @return
	 */
	public StkArticleLot2StrgSctn discoverSection(String artPic, String parentSection) {
		if(StringUtils.isBlank(artPic)) return null;
		Long count = countByArtPic(artPic);
		if(count<=0) return null;
		if(StringUtils.isBlank(parentSection)){
			List<StkArticleLot2StrgSctn> found = findByArtPic(artPic, 0, 1);
			if(!found.isEmpty()) return found.iterator().next();
		}
		
		int processed = 0;
		int max = 100;
		while(processed<count){
			int start = processed;
			processed+=max;
			List<StkArticleLot2StrgSctn> found = findByArtPic(artPic, start, processed);
			for (StkArticleLot2StrgSctn stkArt2Section : found) {
				StkSection stkSection = stkSectionLookup.findByIdentif(stkArt2Section.getSection());
				if(stkSection.isChildOf(parentSection)) return stkArt2Section;
			}
		}

		return null;
	}
}
