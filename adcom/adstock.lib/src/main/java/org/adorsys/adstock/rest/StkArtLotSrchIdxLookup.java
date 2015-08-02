package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.Query;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArtLotSrchIdx;
import org.adorsys.adstock.jpa.StkArtLotSrchIdxSearchInput;
import org.adorsys.adstock.repo.StkArtLotSrchIdxRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StkArtLotSrchIdxLookup extends
		CoreAbstIdentifLookup<StkArtLotSrchIdx> {

	@Inject
	private StkArtLotSrchIdxRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArtLotSrchIdx> getRepo() {
		return repository;
	}

	public StkArtLotSrchIdx findBySectionAndLotPicAndLg(String strgSection, String lotPic, String lg){
		String primaryKey = StkArtLotSrchIdx.toId(lotPic, strgSection, lg);
		return repository.findBy(primaryKey);
	}

	public Long countByArtLMIdx(String artLMIdx){
		return repository.findByArtLMIndex(artLMIdx).count();
	}
	public List<StkArtLotSrchIdx> findByArtLMIdxOrderAsc(String artLMIdx, int start, int max){
		return repository.findByArtLMIndex(artLMIdx).orderAsc("identif").firstResult(start).maxResults(max).getResultList();
	}
	public List<StkArtLotSrchIdx> findByArtLMIdxOrderDesc(String artLMIdx, int start, int max){
		return repository.findByArtLMIndex(artLMIdx).orderDesc("identif").firstResult(start).maxResults(max).getResultList();
	}
	
	@Override
	protected Class<StkArtLotSrchIdx> getEntityClass() {
		return StkArtLotSrchIdx.class;
	}

	public Long countBySectionAndLotPic(String strgSection, String lotPic){
		String cntnrIdentif = StkArtLotSrchIdx.toLotPicAndDectionKey(lotPic, strgSection);
		return repository.findByCntnrIdentif(cntnrIdentif).count();
	}

	public List<StkArtLotSrchIdx> findBySectionAndLotPicOrderAsc(String strgSection, String lotPic, int start, int max){
		String cntnrIdentif = StkArtLotSrchIdx.toLotPicAndDectionKey(lotPic, strgSection);
		return repository.findByCntnrIdentif(cntnrIdentif).orderAsc("identif").firstResult(start).maxResults(max).getResultList();
	}
	public List<StkArtLotSrchIdx> findBySectionAndLotPicOrderDesc(String strgSection, String lotPic, int start, int max){
		String cntnrIdentif = StkArtLotSrchIdx.toLotPicAndDectionKey(lotPic, strgSection);
		return repository.findByCntnrIdentif(cntnrIdentif).orderDesc("identif").firstResult(start).maxResults(max).getResultList();
	}
	
	@Override
	protected StringBuilder preprocessQuery(String findOrCount, CoreAbstIdentifObjectSearchInput<StkArtLotSrchIdx> si){
		
		// Super
		StringBuilder qBuilder = super.preprocessQuery(findOrCount, si);

		StkArtLotSrchIdxSearchInput searchInput = (StkArtLotSrchIdxSearchInput) si;
		boolean whereSet = qBuilder.indexOf(whereClause)>-1;
		StkArtLotSrchIdx entity = searchInput.getEntity();
		
		if(searchInput.getFieldNames().contains("lotPic") && StringUtils.isNotBlank(entity.getLotPic())) whereSet = prep(whereSet, qBuilder, "LOWER(e.lotPic) LIKE(LOWER(:lotPic))");
		if(searchInput.getFieldNames().contains("artPic") && StringUtils.isNotBlank(entity.getArtPic())) whereSet = prep(whereSet, qBuilder, "LOWER(e.artPic) LIKE(LOWER(:artPic))");

		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())) whereSet = prep(whereSet, qBuilder, "LOWER(e.section) LIKE(LOWER(:section))");

		if(searchInput.getFieldNames().contains("artName") && StringUtils.isNotBlank(entity.getArtName())) whereSet = prep(whereSet, qBuilder, "LOWER(e.artName) LIKE(LOWER(:artName))");

		if(searchInput.getExpirDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.expirDt >= :expirDtFrom");
		if(searchInput.getExpirDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.expirDt <= :expirDtTo");
		
		
		if(StringUtils.isNotBlank(searchInput.getRangeStart())) whereSet = prep(whereSet, qBuilder, "e.artName >= :rangeStart");
		if(StringUtils.isNotBlank(searchInput.getRangeEnd())) whereSet = prep(whereSet, qBuilder, "e.artName <= :rangeEnd");

		if(searchInput.getSlsUnitPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcPreTaxFrom >= :slsUnitPrcPreTaxFrom");
		if(searchInput.getSlsUnitPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcPreTaxTo >= :slsUnitPrcPreTaxTo");

		if(searchInput.getSlsUnitPrcTaxInclFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcTaxInclFrom >= :slsUnitPrcTaxInclFrom");
		if(searchInput.getSlsUnitPrcTaxInclTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcTaxInclTo >= :slsUnitPrcTaxInclTo");

		if(searchInput.getSlsMinUnitPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsMinUnitPrcPreTaxFrom >= :slsMinUnitPrcPreTaxFrom");
		if(searchInput.getSlsMinUnitPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsMinUnitPrcPreTaxTo >= :slsMinUnitPrcPreTaxTo");

		if(searchInput.getSlsMinUnitPrcTaxInclFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsMinUnitPrcTaxInclFrom >= :slsMinUnitPrcTaxInclFrom");
		if(searchInput.getSlsMinUnitPrcTaxInclTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsMinUnitPrcTaxInclTo >= :slsMinUnitPrcTaxInclTo");

		if(searchInput.getSlsVatPctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatPctFrom >= :slsVatPctFrom");
		if(searchInput.getSlsVatPctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatPctTo >= :slsVatPctTo");

		return qBuilder;
	}

	@Override
	protected void setParameters(CoreAbstIdentifObjectSearchInput<StkArtLotSrchIdx> si, Query query)
	{
		super.setParameters(si, query);

		StkArtLotSrchIdxSearchInput searchInput = (StkArtLotSrchIdxSearchInput) si;
		StkArtLotSrchIdx entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("lotPic") && StringUtils.isNotBlank(entity.getLotPic())) query.setParameter("lotPic", suffix(entity.getLotPic()));
		if(searchInput.getFieldNames().contains("artPic") && StringUtils.isNotBlank(entity.getArtPic())) query.setParameter("artPic", suffix(entity.getArtPic()));

		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())) query.setParameter("section", suffix(entity.getSection()));
		if(searchInput.getFieldNames().contains("artName") && StringUtils.isNotBlank(entity.getArtName())) query.setParameter("artName", suffix(entity.getArtName()));

		if(searchInput.getExpirDtFrom()!=null) query.setParameter("expirDtFrom", searchInput.getExpirDtFrom());
		if(searchInput.getExpirDtTo()!=null) query.setParameter("expirDtTo", searchInput.getExpirDtTo());

		if(searchInput.getRangeStart()!=null) query.setParameter("rangeStart", searchInput.getRangeStart());
		if(searchInput.getRangeEnd()!=null) query.setParameter("rangeEnd", searchInput.getRangeEnd());

		if(searchInput.getSlsUnitPrcPreTaxFrom()!=null) query.setParameter("slsUnitPrcPreTaxFrom", searchInput.getSlsUnitPrcPreTaxFrom());
		if(searchInput.getSlsUnitPrcPreTaxTo()!=null) query.setParameter("slsUnitPrcPreTaxTo", searchInput.getSlsUnitPrcPreTaxTo());

		if(searchInput.getSlsUnitPrcTaxInclFrom()!=null) query.setParameter("slsUnitPrcTaxInclFrom", searchInput.getSlsUnitPrcTaxInclFrom());
		if(searchInput.getSlsUnitPrcTaxInclTo()!=null) query.setParameter("slsUnitPrcTaxInclTo", searchInput.getSlsUnitPrcTaxInclTo());

		if(searchInput.getSlsMinUnitPrcPreTaxFrom()!=null) query.setParameter("slsMinUnitPrcPreTaxFrom", searchInput.getSlsMinUnitPrcPreTaxFrom());
		if(searchInput.getSlsMinUnitPrcPreTaxTo()!=null) query.setParameter("slsMinUnitPrcPreTaxTo", searchInput.getSlsMinUnitPrcPreTaxTo());

		if(searchInput.getSlsMinUnitPrcTaxInclFrom()!=null) query.setParameter("slsMinUnitPrcTaxInclFrom", searchInput.getSlsMinUnitPrcTaxInclFrom());
		if(searchInput.getSlsMinUnitPrcTaxInclTo()!=null) query.setParameter("slsMinUnitPrcTaxInclTo", searchInput.getSlsMinUnitPrcTaxInclTo());

		if(searchInput.getSlsVatPctFrom()!=null) query.setParameter("slsVatPctFrom", searchInput.getSlsVatPctFrom());
		if(searchInput.getSlsVatPctTo()!=null) query.setParameter("slsVatPctTo", searchInput.getSlsVatPctTo());
	}		
}
