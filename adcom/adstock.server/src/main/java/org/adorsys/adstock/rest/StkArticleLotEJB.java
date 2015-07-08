package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.repo.StkArticleLotRepository;

@Stateless
public class StkArticleLotEJB extends CoreAbstBsnsItemEJB<StkArticleLot>{

	@Inject
	private StkArticleLotRepository repository;
	
	@Inject
	private StkArticleLotLookup lookup;
	
	@EJB
	private StkArticleLotEJB ejb; 
//
//	@Inject
//	CatalArtFeatMappingReaderEJB catalArtFeatMappingReaderEJB;
//
//
//	public StkArticleLot populateStkArticleLot(StkArticleLot entity) {
//		String langIso2 = securityUtil.getUserLange();
//		List<String> listLangIso2 = securityUtil.getUserLangePrefs();
//		if (StringUtils.isBlank(langIso2)) {
//			langIso2 = listLangIso2.get(0);
//		}
//		String identif = CatalArtFeatMapping.toIdentif(entity.getArtPic(),
//				langIso2);
//		CatalArtFeatMapping catalArtFeatMapp = catalArtFeatMappingReaderEJB
//				.findByIdentif(identif);
//
//		entity.setArtFeatures(catalArtFeatMapp);
//		return entity;
//	}

	@Override
	protected CoreAbstBsnsItemRepo<StkArticleLot> getBsnsRepo() {
		return repository;
	}

	@Override
	protected CoreAbstBsnsItemLookup<StkArticleLot> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstBsnsItemEJB<StkArticleLot> getEjb() {
		return ejb;
	}

	@Override
	protected void fireInconsistentEvent(String hldrNbr) {
		// VOID
	}

	@Override
	protected void fireConsistentEvent(String hldrNbr) {
		// VOID
	}

	@Override
	protected Boolean checkSameQty(List<StkArticleLot> compareList) {
		// VOID
		return Boolean.TRUE;
	}

}
