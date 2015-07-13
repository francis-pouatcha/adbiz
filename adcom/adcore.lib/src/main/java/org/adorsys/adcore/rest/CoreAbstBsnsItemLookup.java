package org.adorsys.adcore.rest;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreSortOrder;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.deltaspike.data.api.QueryResult;

public abstract class CoreAbstBsnsItemLookup<E extends CoreAbstBsnsItem> extends CoreAbstIdentifiedLookup<E>{

	protected abstract CoreAbstBsnsItemRepo<E> getBsnsRepo();
	protected abstract Class<E> getBsnsObjClass();
	protected abstract EntityManager getEntityManager();

	protected CoreAbstIdentifDataRepo<E> getRepo(){
		return getBsnsRepo();
	};
	
	public Long countByCntnrIdentif(String cntnrIdentif){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).count();
	}
	
	public List<E> findBCntnrIdentif(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).firstResult(start).maxResults(max).getResultList();
	}
	
	public List<E> findByCntnrIdentifOrdered(String cntnrIdentif, int start, int max, List<CoreSortOrder> sortFieldNames){
		QueryResult<E> q = getBsnsRepo().findByCntnrIdentif(cntnrIdentif);
		Class<E> bsnsObjClass = getBsnsObjClass();
		Set<String> processedFields =  new HashSet<String>();
		for (CoreSortOrder coreSortOrder : sortFieldNames) {
			String fieldName = validateSortField(bsnsObjClass, processedFields, coreSortOrder.getFieldName());
			if(coreSortOrder.getASC()){
				q = q.orderAsc(fieldName);
			} else {
				q = q.orderAsc(fieldName);
			}
		}
		return q.firstResult(start).maxResults(max).getResultList();
	}
	
	public List<E> findByCntnrIdentifOrderByIdentifAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).orderAsc("identif").firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByCntnrIdentifAndIdentifBetween(String cntnrIdentif, String identifStart, String identifEnd){
		return getBsnsRepo().findByCntnrIdentifAndIdentifBetween(cntnrIdentif, identifStart, identifEnd).count();
	}
	
	public List<E> findByCntnrIdentifAndIdentifBetweenOrderByIdentifAsc(String cntnrIdentif, String identifStart, String identifEnd, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndIdentifBetween(cntnrIdentif, identifStart, identifEnd).orderAsc("identif").firstResult(start).maxResults(max).getResultList();
	}

	public List<E> findByCntnrIdentifOrderBySalIndexAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).orderAsc("salIndex").firstResult(start).maxResults(max).getResultList();
	}

	public List<E> findByCntnrIdentifOrderByAcsngDtAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).orderAsc("acsngDt").firstResult(start).maxResults(max).getResultList();
	}  

	public List<E> findByCntnrIdentifOrderBySectionAscArtNameAsc(String cntnrIdentif, int start, int max, String artNameOrderField){
		return getBsnsRepo().findByCntnrIdentif(cntnrIdentif).orderAsc("section").orderAsc(artNameOrderField).firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByCntnrIdentifAndDisabledDtIsNull(String cntnrIdentif){
		return getBsnsRepo().findByCntnrIdentifAndDisabledDtIsNull(cntnrIdentif).count();
	}
	public List<E> findByCntnrIdentifAndDisabledDtIsNullOrderByArtNameAsc(String cntnrIdentif, int start, int max, String artNameOrderField){
		return getBsnsRepo().findByCntnrIdentifAndDisabledDtIsNull(cntnrIdentif).orderAsc(artNameOrderField).firstResult(start).maxResults(max).getResultList();
	}  
	public List<E> findByCntnrIdentifAndDisabledDtIsNullOrderByAcsngDtAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndDisabledDtIsNull(cntnrIdentif).orderAsc("acsngDt").firstResult(start).maxResults(max).getResultList();
	}  

	public Long countByCntnrIdentifAndSection(String cntnrIdentif, String section){
		return getBsnsRepo().findByCntnrIdentifAndSection(cntnrIdentif, section).count();
	}
	public List<E> findByCntnrIdentifAndSection(String cntnrIdentif, String section, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndSection(cntnrIdentif,section).firstResult(start).maxResults(max).getResultList();
	}  
	public List<E> findByCntnrIdentifAndSectionOrderByArtNameAsc(String cntnrIdentif, String section, int start, int max, String artNameOrderField){
		return getBsnsRepo().findByCntnrIdentifAndSection(cntnrIdentif,section).orderAsc(artNameOrderField).firstResult(start).maxResults(max).getResultList();
	}  
	public List<E> findByCntnrIdentifAndSectionOrderBySalIndexAsc(String cntnrIdentif,String section, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndSection(cntnrIdentif, section).orderAsc("salIndex").firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByCntnrIdentifAndArtPic(String cntnrIdentif, String artPic){
		return getBsnsRepo().findByCntnrIdentifAndArtPic(cntnrIdentif, artPic).count();
	}
	public List<E> findByCntnrIdentifAndArtPic(String cntnrIdentif, String artPic, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndArtPic(cntnrIdentif, artPic).firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByCntnrIdentifAndArtPicAndOuIdentif(String cntnrIdentif,String artPic, String ouIdentif){
		return getBsnsRepo().findByCntnrIdentifAndArtPicAndOuIdentif(cntnrIdentif, artPic, ouIdentif).count();
	}
	public List<E> findByCntnrIdentifAndArtPicAndOuIdentif(String cntnrIdentif,String artPic, String ouIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndArtPicAndOuIdentif(cntnrIdentif, artPic, ouIdentif).firstResult(start).maxResults(max).getResultList();		
	}
	
	public Long countByCntnrIdentifAndArtPicAndSection(String cntnrIdentif,String artPic, String section){
		return getBsnsRepo().findByCntnrIdentifAndArtPicAndSection(cntnrIdentif, artPic, section).count();
	}
	public List<E> findByCntnrIdentifAndArtPicAndSection(String cntnrIdentif,String artPic, String section, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndArtPicAndSection(cntnrIdentif, artPic, section).firstResult(start).maxResults(max).getResultList();		
	}
	
	public Long countByCntnrIdentifAndSalIndex(String cntnrIdentif, String salIndex){
		return getBsnsRepo().findByCntnrIdentifAndSalIndex(cntnrIdentif, salIndex).count();
	}
	public List<E> findByCntnrIdentifAndSalIndex(String cntnrIdentif, String salIndex, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndSalIndex(cntnrIdentif, salIndex).firstResult(start).maxResults(max).getResultList();	
	}

	
	public Long countByCntnrIdentifAndDisabledDtIsNotNull(String cntnrIdentif){
		return getBsnsRepo().findByCntnrIdentifAndDisabledDtIsNotNull(cntnrIdentif).count();
	}  
	public List<E> findByCntnrIdentifAndDisabledDtIsNotNullOrderBySalIndexAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndDisabledDtIsNotNull(cntnrIdentif).orderAsc("salIndex").firstResult(start).maxResults(max).getResultList();
	}
	
	
	public Long countByCntnrIdentifAndSalIndexAndDisabledDtIsNull(String cntnrIdentif, String salIndex){
		return getBsnsRepo().findByCntnrIdentifAndSalIndexAndDisabledDtIsNull(cntnrIdentif, salIndex).count();
	}  
	public List<E> findByCntnrIdentifAndSalIndexAndDisabledDtIsNull(String cntnrIdentif, String salIndex, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndSalIndexAndDisabledDtIsNull(cntnrIdentif, salIndex).firstResult(start).maxResults(max).getResultList();
	}  

	
	public Long countByCntnrIdentifAndSalIndexAndDisabledDtIsNotNull(String cntnrIdentif, String salIndex){
		return getBsnsRepo().findByCntnrIdentifAndSalIndexAndDisabledDtIsNotNull(cntnrIdentif, salIndex).count();
	}  
	public List<E> findByCntnrIdentifAndSalIndexAndDisabledDtIsNotNull(String cntnrIdentif, String salIndex, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndSalIndexAndDisabledDtIsNotNull(cntnrIdentif, salIndex).firstResult(start).maxResults(max).getResultList();
	}

	
	public Long countByCntnrIdentifAndConflictDtIsNotNull(String cntnrIdentif){
		return getBsnsRepo().findByCntnrIdentifAndConflictDtIsNotNull(cntnrIdentif).count();
	}
	public List<E> findByCntnrIdentifAndConflictDtIsNotNullOrderBySalIndexAsc(String cntnrIdentif, int start, int max){
		return getBsnsRepo().findByCntnrIdentifAndConflictDtIsNotNull(cntnrIdentif).orderAsc("salIndex").firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByArtPicLike(String artPic){
		return getBsnsRepo().findByArtPicLike(StringUtils.lowerCase(artPic)+"%").count();
	}
	public List<E> findByArtPicLike(String artPic, int start, int max){
		return getBsnsRepo().findByArtPicLike(StringUtils.lowerCase(artPic)+"%").firstResult(start).maxResults(max).getResultList();
	}

	public Long countBySupplierPicLike(String supplierPic){
		return getBsnsRepo().findBySupplierPicLike(StringUtils.lowerCase(supplierPic)+"%").count();
	}
	public List<E> findBySupplierPicLike(String supplierPic, int start, int max){
		return getBsnsRepo().findBySupplierPicLike(StringUtils.lowerCase(supplierPic)+"%").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByLotPicLikeOrArtPicLikeOrSupplierPicLike(String lotPic, String artPic, String supplierPic){
		return getBsnsRepo().findByLotPicLikeOrArtPicLikeOrSupplierPicLike(StringUtils.lowerCase(lotPic)+"%", StringUtils.lowerCase(artPic)+"%", StringUtils.lowerCase(supplierPic)+"%").count();
	}
	public List<E> findByLotPicLikeOrArtPicLikeOrSupplierPicLike(String lotPic, String artPic, String supplierPic, int start, int max){
		return getBsnsRepo().findByLotPicLikeOrArtPicLikeOrSupplierPicLike(StringUtils.lowerCase(lotPic)+"%", StringUtils.lowerCase(artPic)+"%", StringUtils.lowerCase(supplierPic)+"%").firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByLotPicLike(String lotPic){
		return getBsnsRepo().findByLotPicLike(StringUtils.lowerCase(lotPic)+"%").count();
	}
	public List<E> findByLotPicLike(String lotPic, int start, int max){
		return getBsnsRepo().findByLotPicLike(StringUtils.lowerCase(lotPic)+"%").firstResult(start).maxResults(max).getResultList();
	}
	
	public Long countByArtPic(String artPic){
		return getBsnsRepo().findByArtPic(artPic).count();
	}
	public List<E> findByArtPicOrderByLotPic(String artPic, int start, int max){
		return getBsnsRepo().findByArtPic(artPic).orderAsc("lotPic").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByExpirDtBetween(Date fromDt, Date toDt){
		return getBsnsRepo().findByExpirDtBetween(fromDt, toDt).count();
	}
	public List<E> findByExpirDtBetween(Date fromDt, Date toDt, int start, int max){
		return getBsnsRepo().findByExpirDtBetween(fromDt, toDt).firstResult(start).maxResults(max).getResultList();		
	}

	public Long countBySupplierAndExpirDtBetween(String supplier, Date fromDt, Date toDt){
		return getBsnsRepo().findBySupplierAndExpirDtBetween(supplier, fromDt, toDt).count();		
	}
	public List<E> findBySupplierAndExpirDtBetween(String supplier, Date fromDt, Date toDt, int start, int max){
		return getBsnsRepo().findBySupplierAndExpirDtBetween(supplier, fromDt, toDt).firstResult(start).maxResults(max).getResultList();		
	}
	
	/**
	 * Returns true if the inventory object is consistent. Means that there is only one enabled item for each
	 * inventory item. An inventory can have many inventory items if counted by many employees.
	 * 
	 * @param cntnrIdentif
	 * @return true : if consistent.
	 */
	public Boolean checkConsistent(String cntnrIdentif){
		Long countByCntnrIdentif = countByCntnrIdentif(cntnrIdentif);
		int start = 0;
		int max = 100;
		String salIndex = null;
		boolean cleanIndex = false;
		while(start<=countByCntnrIdentif){
			// @WARNIGN increase counter before request to avoid endless loop on error. 
			int firstResult = start;
			start +=max;
			List<E> found = findByCntnrIdentifOrderBySalIndexAsc(cntnrIdentif, firstResult, max);
			for (E invInvtryItem : found) {
				if(StringUtils.equals(salIndex, invInvtryItem.getSalIndex()) && !cleanIndex){
					Long enbld = countByCntnrIdentifAndSalIndexAndDisabledDtIsNull(cntnrIdentif, salIndex);
					if(enbld==1) {
						cleanIndex=true;
						continue;
					} else {
						return false;
					}
				} else {
					salIndex = invInvtryItem.getSalIndex();
					cleanIndex = false;
				}
			}			
		}
		return true;
	}

	private final String FIND_CUSTOM_QUERY = "SELECT e FROM "+ getBsnsObjClass().getSimpleName() +" AS e";
	private final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM "+ getBsnsObjClass().getSimpleName() +" AS e";
	String whereClause = " WHERE ";
	String andClause = " AND ";
	String orderBy = "ORDER BY e.";
	String orderASC = " ASC";
	String orderDESC = " DESC";
	
	private StringBuilder preprocessQuery(String findOrCount, CoreAbstBsnsItemSearchInput<E> searchInput){
		E entity = searchInput.getEntity();


		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())) whereSet = prep(whereSet, qBuilder, "e.cntnrIdentif=:cntnrIdentif");
		
		if(searchInput.getValueDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.valueDt >= :valueDtFrom");
		if(searchInput.getValueDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.valueDt <= :valueDtTo");

		if(searchInput.getIdentifFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.identif >= :identifFrom");
		if(searchInput.getIdentifTo()!=null) whereSet = prep(whereSet, qBuilder, "e.identif <= :identifTo");
		
		if(searchInput.getFieldNames().contains("lotPic") && StringUtils.isNotBlank(entity.getLotPic())) whereSet = prep(whereSet, qBuilder, "LOWER(e.lotPic) LIKE(LOWER(:lotPic))");
		if(searchInput.getFieldNames().contains("artPic") && StringUtils.isNotBlank(entity.getArtPic())) whereSet = prep(whereSet, qBuilder, "LOWER(e.artPic) LIKE(LOWER(:artPic))");

		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())) whereSet = prep(whereSet, qBuilder, "LOWER(e.section) LIKE(LOWER(:section))");
		if(searchInput.getFieldNames().contains("supplier") && StringUtils.isNotBlank(entity.getSupplier())) whereSet = prep(whereSet, qBuilder, "LOWER(e.supplier) LIKE(LOWER(:supplier))");

		if(searchInput.getFieldNames().contains("supplierPic") && StringUtils.isNotBlank(entity.getSupplierPic())) whereSet = prep(whereSet, qBuilder, "LOWER(e.supplierPic) LIKE(LOWER(:supplierPic))");

		if(searchInput.getExpirDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.expirDt >= :expirDtFrom");
		if(searchInput.getExpirDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.expirDt <= :expirDtTo");

		if(searchInput.getPrchUnitPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchUnitPrcPreTaxFrom >= :prchUnitPrcPreTaxFrom");
		if(searchInput.getPrchUnitPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchUnitPrcPreTaxTo >= :prchUnitPrcPreTaxTo");

		if(searchInput.getPrchRstckgFeesPrctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPrctFrom >= :prchRstckgFeesPrctFrom");
		if(searchInput.getPrchRstckgFeesPrctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPrctTo >= :prchRstckgFeesPrctTo");

		if(searchInput.getPrchRstckgFeesPrctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPrctFrom >= :prchRstckgFeesPrctFrom");
		if(searchInput.getPrchRstckgFeesPrctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPrctTo >= :prchRstckgFeesPrctTo");

		if(searchInput.getPrchRstckgUnitFeesPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgUnitFeesPreTaxFrom >= :prchRstckgUnitFeesPreTaxFrom");
		if(searchInput.getPrchRstckgUnitFeesPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgUnitFeesPreTaxTo >= :prchRstckgUnitFeesPreTaxTo");

		if(searchInput.getPrchRstckgFeesPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPreTaxFrom >= :prchRstckgFeesPreTaxFrom");
		if(searchInput.getPrchRstckgFeesPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRstckgFeesPreTaxTo >= :prchRstckgFeesPreTaxTo");

		if(searchInput.getPrchGrossPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchGrossPrcPreTaxFrom >= :prchGrossPrcPreTaxFrom");
		if(searchInput.getPrchGrossPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchGrossPrcPreTaxTo >= :prchGrossPrcPreTaxTo");

		if(searchInput.getPrchRebateAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebateAmtFrom >= :prchRebateAmtFrom");
		if(searchInput.getPrchRebateAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebateAmtTo >= :prchRebateAmtTo");

		if(searchInput.getPrchRebatePctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebatePctFrom >= :prchRebatePctFrom");
		if(searchInput.getPrchRebatePctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRebatePctTo >= :prchRebatePctTo");

		if(searchInput.getPrchNetPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchNetPrcPreTaxFrom >= :prchNetPrcPreTaxFrom");
		if(searchInput.getPrchNetPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchNetPrcPreTaxTo >= :prchNetPrcPreTaxTo");

		if(searchInput.getPrchVatPctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchVatPctFrom >= :prchVatPctFrom");
		if(searchInput.getPrchVatPctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchVatPctTo >= :prchVatPctTo");

		if(searchInput.getPrchVatAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchVatAmtFrom >= :prchVatAmtFrom");
		if(searchInput.getPrchVatAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchVatAmtTo >= :prchVatAmtTo");

		if(searchInput.getPrchNetPrcTaxInclFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchNetPrcTaxInclFrom >= :prchNetPrcTaxInclFrom");
		if(searchInput.getPrchNetPrcTaxInclTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchNetPrcTaxInclTo >= :prchNetPrcTaxInclTo");

		if(searchInput.getPrchWrntyDaysFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchWrntyDaysFrom >= :prchWrntyDaysFrom");
		if(searchInput.getPrchWrntyDaysTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchWrntyDaysTo >= :prchWrntyDaysTo");

		if(searchInput.getPrchRtrnDaysFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRtrnDaysFrom >= :prchRtrnDaysFrom");
		if(searchInput.getPrchRtrnDaysTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRtrnDaysTo >= :prchRtrnDaysTo");

		if(searchInput.getPrchWrntyDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchWrntyDtFrom >= :prchWrntyDtFrom");
		if(searchInput.getPrchWrntyDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchWrntyDtTo >= :prchWrntyDtTo");

		if(searchInput.getPrchRtrnDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRtrnDtFrom >= :prchRtrnDtFrom");
		if(searchInput.getPrchRtrnDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.prchRtrnDtTo >= :prchRtrnDtTo");
		
		if(searchInput.getSlsUnitPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcPreTaxFrom >= :slsUnitPrcPreTaxFrom");
		if(searchInput.getSlsUnitPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsUnitPrcPreTaxTo >= :slsUnitPrcPreTaxTo");

		if(searchInput.getSlsRstckgFeesPrctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPrctFrom >= :slsRstckgFeesPrctFrom");
		if(searchInput.getSlsRstckgFeesPrctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPrctTo >= :slsRstckgFeesPrctTo");

		if(searchInput.getSlsRstckgFeesPrctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPrctFrom >= :slsRstckgFeesPrctFrom");
		if(searchInput.getSlsRstckgFeesPrctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPrctTo >= :slsRstckgFeesPrctTo");

		if(searchInput.getSlsRstckgUnitFeesPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgUnitFeesPreTaxFrom >= :slsRstckgUnitFeesPreTaxFrom");
		if(searchInput.getSlsRstckgUnitFeesPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgUnitFeesPreTaxTo >= :slsRstckgUnitFeesPreTaxTo");

		if(searchInput.getSlsRstckgFeesPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPreTaxFrom >= :slsRstckgFeesPreTaxFrom");
		if(searchInput.getSlsRstckgFeesPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRstckgFeesPreTaxTo >= :slsRstckgFeesPreTaxTo");

		if(searchInput.getSlsGrossPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsGrossPrcPreTaxFrom >= :slsGrossPrcPreTaxFrom");
		if(searchInput.getSlsGrossPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsGrossPrcPreTaxTo >= :slsGrossPrcPreTaxTo");

		if(searchInput.getSlsRebateAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebateAmtFrom >= :slsRebateAmtFrom");
		if(searchInput.getSlsRebateAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebateAmtTo >= :slsRebateAmtTo");

		if(searchInput.getSlsRebatePctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebatePctFrom >= :slsRebatePctFrom");
		if(searchInput.getSlsRebatePctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRebatePctTo >= :slsRebatePctTo");

		if(searchInput.getSlsNetPrcPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsNetPrcPreTaxFrom >= :slsNetPrcPreTaxFrom");
		if(searchInput.getSlsNetPrcPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsNetPrcPreTaxTo >= :slsNetPrcPreTaxTo");

		if(searchInput.getSlsVatPctFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatPctFrom >= :slsVatPctFrom");
		if(searchInput.getSlsVatPctTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatPctTo >= :slsVatPctTo");

		if(searchInput.getSlsVatAmtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatAmtFrom >= :slsVatAmtFrom");
		if(searchInput.getSlsVatAmtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsVatAmtTo >= :slsVatAmtTo");

		if(searchInput.getSlsNetPrcTaxInclFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsNetPrcTaxInclFrom >= :slsNetPrcTaxInclFrom");
		if(searchInput.getSlsNetPrcTaxInclTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsNetPrcTaxInclTo >= :slsNetPrcTaxInclTo");

		if(searchInput.getSlsWrntyDaysFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsWrntyDaysFrom >= :slsWrntyDaysFrom");
		if(searchInput.getSlsWrntyDaysTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsWrntyDaysTo >= :slsWrntyDaysTo");

		if(searchInput.getSlsRtrnDaysFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRtrnDaysFrom >= :slsRtrnDaysFrom");
		if(searchInput.getSlsRtrnDaysTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRtrnDaysTo >= :slsRtrnDaysTo");

		if(searchInput.getSlsWrntyDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsWrntyDtFrom >= :slsWrntyDtFrom");
		if(searchInput.getSlsWrntyDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsWrntyDtTo >= :slsWrntyDtTo");

		if(searchInput.getSlsRtrnDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRtrnDtFrom >= :slsRtrnDtFrom");
		if(searchInput.getSlsRtrnDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.slsRtrnDtTo >= :slsRtrnDtTo");
		
		if(searchInput.getStkUnitValPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.stkUnitValPreTaxFrom >= :stkUnitValPreTaxFrom");
		if(searchInput.getStkUnitValPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.stkUnitValPreTaxTo >= :stkUnitValPreTaxTo");
		
		if(searchInput.getStkValPreTaxFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.stkValPreTaxFrom >= :stkValPreTaxFrom");
		if(searchInput.getStkValPreTaxTo()!=null) whereSet = prep(whereSet, qBuilder, "e.stkValPreTaxTo >= :stkValPreTaxTo");
		
		if(searchInput.getConflictDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.conflictDtFrom >= :conflictDtFrom");
		if(searchInput.getConflictDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.conflictDtTo >= :conflictDtTo");
		
		if(searchInput.getDisabledDtFrom()!=null) whereSet = prep(whereSet, qBuilder, "e.disabledDtFrom >= :disabledDtFrom");
		if(searchInput.getDisabledDtTo()!=null) whereSet = prep(whereSet, qBuilder, "e.disabledDtTo >= :disabledDtTo");
		
		List<CoreSortOrder> sortFieldNames = searchInput.getSortFieldNames();
		Set<String> processedFields =  new HashSet<String>();
		Class<E> bsnsObjClass = getBsnsObjClass();
		for (CoreSortOrder coreSortOrder : sortFieldNames) {
			String fieldName = validateSortField(bsnsObjClass, processedFields, coreSortOrder.getFieldName());
			if(StringUtils.isBlank(fieldName)) continue;
			qBuilder.append(orderBy).append(fieldName);
			if(coreSortOrder.getASC()){
				qBuilder.append(orderASC);	
			} else {
				qBuilder.append(orderDESC);	
			}
		}
		return qBuilder;
	}
	
	private String validateSortField(Class<E> bsnsObjClass, Set<String> processedFields, String fieldName){
		fieldName = StringUtils.trim(fieldName);
		if(StringUtils.isBlank(fieldName)) return null;
		if(processedFields.contains(fieldName)) return null;
		Field field = FieldUtils.getField(bsnsObjClass, fieldName, true);
		if(field==null) return null;
		
		processedFields.add(fieldName);
		return fieldName;
	}
	
	public void setParameters(CoreAbstBsnsItemSearchInput<E> searchInput, Query query)
	{
		E entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("cntnrIdentif") && StringUtils.isNotBlank(entity.getCntnrIdentif())) query.setParameter("cntnrIdentif", entity.getCntnrIdentif());;

		
		if(searchInput.getValueDtFrom()!=null) query.setParameter("valueDtFrom", searchInput.getValueDtFrom());
		if(searchInput.getValueDtTo()!=null) query.setParameter("valueDtTo", searchInput.getValueDtTo());
		
		if(searchInput.getIdentifFrom()!=null) query.setParameter("identifFrom", searchInput.getIdentifFrom());
		if(searchInput.getIdentifTo()!=null) query.setParameter("identifTo", searchInput.getIdentifTo());

		if(searchInput.getFieldNames().contains("lotPic") && StringUtils.isNotBlank(entity.getLotPic())) query.setParameter("lotPic", suffix(entity.getLotPic()));
		if(searchInput.getFieldNames().contains("artPic") && StringUtils.isNotBlank(entity.getArtPic())) query.setParameter("artPic", suffix(entity.getArtPic()));

		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())) query.setParameter("section", suffix(entity.getSection()));
		if(searchInput.getFieldNames().contains("supplier") && StringUtils.isNotBlank(entity.getSupplier())) query.setParameter("supplier", suffix(entity.getSupplier()));

		if(searchInput.getFieldNames().contains("supplierPic") && StringUtils.isNotBlank(entity.getSupplierPic())) query.setParameter("supplierPic", suffix(entity.getSupplierPic()));

		if(searchInput.getExpirDtFrom()!=null) query.setParameter("valueDtFrom", searchInput.getExpirDtFrom());
		if(searchInput.getExpirDtTo()!=null) query.setParameter("valueDtTo", searchInput.getExpirDtTo());

		if(searchInput.getPrchUnitPrcPreTaxFrom()!=null) query.setParameter("prchUnitPrcPreTaxFrom", searchInput.getPrchUnitPrcPreTaxFrom());
		if(searchInput.getPrchUnitPrcPreTaxTo()!=null) query.setParameter("prchUnitPrcPreTaxTo", searchInput.getPrchUnitPrcPreTaxTo());

		if(searchInput.getPrchRstckgFeesPrctFrom()!=null) query.setParameter("prchRstckgFeesPrctFrom", searchInput.getPrchRstckgFeesPrctFrom());
		if(searchInput.getPrchRstckgFeesPrctTo()!=null) query.setParameter("prchRstckgFeesPrctTo", searchInput.getPrchRstckgFeesPrctTo());

		if(searchInput.getPrchRstckgFeesPrctFrom()!=null) query.setParameter("prchRstckgFeesPrctFrom", searchInput.getPrchRstckgFeesPrctFrom());
		if(searchInput.getPrchRstckgFeesPrctTo()!=null) query.setParameter("prchRstckgFeesPrctTo", searchInput.getPrchRstckgFeesPrctTo());

		if(searchInput.getPrchRstckgUnitFeesPreTaxFrom()!=null) query.setParameter("prchRstckgUnitFeesPreTaxFrom", searchInput.getPrchRstckgUnitFeesPreTaxFrom());
		if(searchInput.getPrchRstckgUnitFeesPreTaxTo()!=null) query.setParameter("prchRstckgUnitFeesPreTaxTo", searchInput.getPrchRstckgFeesPrctTo());

		if(searchInput.getPrchRstckgFeesPreTaxFrom()!=null) query.setParameter("prchRstckgFeesPreTaxFrom", searchInput.getPrchRstckgFeesPreTaxFrom());
		if(searchInput.getPrchRstckgFeesPreTaxTo()!=null) query.setParameter("prchRstckgFeesPreTaxTo", searchInput.getPrchRstckgFeesPreTaxTo());

		if(searchInput.getPrchGrossPrcPreTaxFrom()!=null) query.setParameter("prchGrossPrcPreTaxFrom", searchInput.getPrchGrossPrcPreTaxFrom());
		if(searchInput.getPrchGrossPrcPreTaxTo()!=null) query.setParameter("prchGrossPrcPreTaxTo", searchInput.getPrchGrossPrcPreTaxTo());

		if(searchInput.getPrchRebateAmtFrom()!=null) query.setParameter("prchRebateAmtFrom", searchInput.getPrchRebateAmtFrom());
		if(searchInput.getPrchRebateAmtTo()!=null) query.setParameter("prchRebateAmtTo", searchInput.getPrchRebateAmtTo());

		if(searchInput.getPrchRebatePctFrom()!=null) query.setParameter("prchRebatePctFrom", searchInput.getPrchRebatePctFrom());
		if(searchInput.getPrchRebatePctTo()!=null) query.setParameter("prchRebatePctTo", searchInput.getPrchRebatePctTo());

		if(searchInput.getPrchNetPrcPreTaxFrom()!=null) query.setParameter("prchNetPrcPreTaxFrom", searchInput.getPrchNetPrcPreTaxFrom());
		if(searchInput.getPrchNetPrcPreTaxTo()!=null) query.setParameter("prchNetPrcPreTaxTo", searchInput.getPrchNetPrcPreTaxTo());

		if(searchInput.getPrchVatPctFrom()!=null) query.setParameter("prchVatPctFrom", searchInput.getPrchVatPctFrom());
		if(searchInput.getPrchVatPctTo()!=null) query.setParameter("prchVatPctTo", searchInput.getPrchVatPctTo());

		if(searchInput.getPrchVatAmtFrom()!=null) query.setParameter("prchVatAmtFrom", searchInput.getPrchVatAmtFrom());
		if(searchInput.getPrchVatAmtTo()!=null) query.setParameter("prchVatAmtTo", searchInput.getPrchVatAmtTo());

		if(searchInput.getPrchNetPrcTaxInclFrom()!=null) query.setParameter("prchNetPrcTaxInclFrom", searchInput.getPrchNetPrcTaxInclFrom());
		if(searchInput.getPrchNetPrcTaxInclTo()!=null) query.setParameter("prchNetPrcTaxInclTo", searchInput.getPrchNetPrcTaxInclTo());

		if(searchInput.getPrchWrntyDaysFrom()!=null) query.setParameter("prchWrntyDaysFrom", searchInput.getPrchWrntyDaysFrom());
		if(searchInput.getPrchWrntyDaysTo()!=null) query.setParameter("prchWrntyDaysTo", searchInput.getPrchWrntyDaysTo());

		if(searchInput.getPrchRtrnDaysFrom()!=null) query.setParameter("prchRtrnDaysFrom", searchInput.getPrchRtrnDaysFrom());
		if(searchInput.getPrchRtrnDaysTo()!=null) query.setParameter("prchRtrnDaysTo", searchInput.getPrchRtrnDaysTo());

		if(searchInput.getPrchWrntyDtFrom()!=null) query.setParameter("prchWrntyDtFrom", searchInput.getPrchWrntyDtFrom());
		if(searchInput.getPrchWrntyDtTo()!=null) query.setParameter("prchWrntyDtTo", searchInput.getPrchWrntyDtTo());

		if(searchInput.getPrchRtrnDtFrom()!=null) query.setParameter("prchRtrnDtFrom", searchInput.getPrchRtrnDtFrom());
		if(searchInput.getPrchRtrnDtTo()!=null) query.setParameter("prchRtrnDtTo", searchInput.getPrchRtrnDtTo());
		
		if(searchInput.getSlsUnitPrcPreTaxFrom()!=null) query.setParameter("slsUnitPrcPreTaxFrom", searchInput.getSlsUnitPrcPreTaxFrom());
		if(searchInput.getSlsUnitPrcPreTaxTo()!=null) query.setParameter("slsUnitPrcPreTaxTo", searchInput.getSlsUnitPrcPreTaxTo());

		if(searchInput.getSlsRstckgFeesPrctFrom()!=null) query.setParameter("slsRstckgFeesPrctFrom", searchInput.getSlsRstckgFeesPrctFrom());
		if(searchInput.getSlsRstckgFeesPrctTo()!=null) query.setParameter("slsRstckgFeesPrctTo", searchInput.getSlsRstckgFeesPrctTo());

		if(searchInput.getSlsRstckgFeesPrctFrom()!=null) query.setParameter("slsRstckgFeesPrctFrom", searchInput.getSlsRstckgFeesPrctFrom());
		if(searchInput.getSlsRstckgFeesPrctTo()!=null) query.setParameter("slsRstckgFeesPrctTo", searchInput.getSlsRstckgFeesPrctTo());

		if(searchInput.getSlsRstckgUnitFeesPreTaxFrom()!=null) query.setParameter("slsRstckgUnitFeesPreTaxFrom", searchInput.getSlsRstckgUnitFeesPreTaxFrom());
		if(searchInput.getSlsRstckgUnitFeesPreTaxTo()!=null) query.setParameter("slsRstckgUnitFeesPreTaxTo", searchInput.getSlsRstckgUnitFeesPreTaxTo());

		if(searchInput.getSlsRstckgFeesPreTaxFrom()!=null) query.setParameter("slsRstckgFeesPreTaxFrom", searchInput.getSlsRstckgFeesPreTaxFrom());
		if(searchInput.getSlsRstckgFeesPreTaxTo()!=null) query.setParameter("slsRstckgFeesPreTaxTo", searchInput.getSlsRstckgFeesPreTaxTo());

		if(searchInput.getSlsGrossPrcPreTaxFrom()!=null) query.setParameter("slsGrossPrcPreTaxFrom", searchInput.getSlsGrossPrcPreTaxFrom());
		if(searchInput.getSlsGrossPrcPreTaxTo()!=null) query.setParameter("slsGrossPrcPreTaxTo", searchInput.getSlsGrossPrcPreTaxTo());

		if(searchInput.getSlsRebateAmtFrom()!=null) query.setParameter("slsRebateAmtFrom", searchInput.getSlsRebateAmtFrom());
		if(searchInput.getSlsRebateAmtTo()!=null) query.setParameter("slsRebateAmtTo", searchInput.getSlsRebateAmtTo());

		if(searchInput.getSlsRebatePctFrom()!=null) query.setParameter("slsRebatePctFrom", searchInput.getSlsRebatePctFrom());
		if(searchInput.getSlsRebatePctTo()!=null) query.setParameter("slsRebatePctTo", searchInput.getSlsRebatePctTo());

		if(searchInput.getSlsNetPrcPreTaxFrom()!=null) query.setParameter("slsNetPrcPreTaxFrom", searchInput.getSlsNetPrcPreTaxFrom());
		if(searchInput.getSlsNetPrcPreTaxTo()!=null) query.setParameter("slsNetPrcPreTaxTo", searchInput.getSlsNetPrcPreTaxTo());

		if(searchInput.getSlsVatPctFrom()!=null) query.setParameter("slsVatPctFrom", searchInput.getSlsVatPctFrom());
		if(searchInput.getSlsVatPctTo()!=null) query.setParameter("slsVatPctTo", searchInput.getSlsVatPctTo());

		if(searchInput.getSlsVatAmtFrom()!=null) query.setParameter("slsVatAmtFrom", searchInput.getSlsVatAmtFrom());
		if(searchInput.getSlsVatAmtTo()!=null) query.setParameter("slsVatAmtTo", searchInput.getSlsVatAmtTo());

		if(searchInput.getSlsNetPrcTaxInclFrom()!=null) query.setParameter("slsNetPrcTaxInclFrom", searchInput.getSlsNetPrcTaxInclFrom());
		if(searchInput.getSlsNetPrcTaxInclTo()!=null) query.setParameter("slsNetPrcTaxInclTo", searchInput.getSlsNetPrcTaxInclTo());

		if(searchInput.getSlsWrntyDaysFrom()!=null) query.setParameter("slsWrntyDaysFrom", searchInput.getSlsWrntyDaysFrom());
		if(searchInput.getSlsWrntyDaysTo()!=null) query.setParameter("slsWrntyDaysTo", searchInput.getSlsWrntyDaysTo());

		if(searchInput.getSlsRtrnDaysFrom()!=null) query.setParameter("slsRtrnDaysFrom", searchInput.getSlsRtrnDaysFrom());
		if(searchInput.getSlsRtrnDaysTo()!=null) query.setParameter("slsRtrnDaysTo", searchInput.getSlsRtrnDaysTo());

		if(searchInput.getSlsWrntyDtFrom()!=null) query.setParameter("slsWrntyDtFrom", searchInput.getSlsWrntyDtFrom());
		if(searchInput.getSlsWrntyDtTo()!=null) query.setParameter("slsWrntyDtTo", searchInput.getSlsWrntyDtTo());

		if(searchInput.getSlsRtrnDtFrom()!=null) query.setParameter("slsRtrnDtFrom", searchInput.getSlsRtrnDtFrom());
		if(searchInput.getSlsRtrnDtTo()!=null) query.setParameter("slsRtrnDtTo", searchInput.getSlsRtrnDtTo());
		
		if(searchInput.getStkUnitValPreTaxFrom()!=null) query.setParameter("stkUnitValPreTaxFrom", searchInput.getStkUnitValPreTaxFrom());
		if(searchInput.getStkUnitValPreTaxTo()!=null) query.setParameter("stkUnitValPreTaxTo", searchInput.getStkUnitValPreTaxTo());
		
		if(searchInput.getStkValPreTaxFrom()!=null) query.setParameter("stkValPreTaxFrom", searchInput.getStkValPreTaxFrom());
		if(searchInput.getStkValPreTaxTo()!=null) query.setParameter("stkValPreTaxTo", searchInput.getStkValPreTaxTo());
		
		if(searchInput.getConflictDtFrom()!=null) query.setParameter("conflictDtFrom", searchInput.getConflictDtFrom());
		if(searchInput.getConflictDtTo()!=null) query.setParameter("conflictDtTo", searchInput.getConflictDtTo());
		
		if(searchInput.getDisabledDtFrom()!=null) query.setParameter("disabledDtFrom", searchInput.getDisabledDtFrom());
		if(searchInput.getDisabledDtTo()!=null) query.setParameter("disabledDtTo", searchInput.getDisabledDtTo());
	}	
	
	private String suffix(String section) {
		if(StringUtils.isBlank(section)) return section;
		if(!StringUtils.endsWith(section, "%")) return section + "%";
		return section;
	}

	private String prefixSuffix(String section) {
		if(StringUtils.isBlank(section)) return section;
		if(!StringUtils.endsWith(section, "%")) return section + "%";
		if(!StringUtils.startsWith(section, "%")) return "%" + section;
		return section;
	}

	public List<E> findCustom(CoreAbstBsnsItemSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<E> query = getEntityManager().createQuery(qBuilder.toString(), getBsnsObjClass());
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		
		return query.getResultList();
	}

	public Long countCustom(CoreAbstBsnsItemSearchInput<E> searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = getEntityManager().createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}
	
	private boolean prep(boolean whereSet, StringBuilder qBuilder, String whereOp){
		if(!whereSet){
			qBuilder.append(whereClause);
			whereSet = true;
		} else {
			qBuilder.append(andClause);
		}
		qBuilder.append(whereOp);
		return whereSet;
	}
}
