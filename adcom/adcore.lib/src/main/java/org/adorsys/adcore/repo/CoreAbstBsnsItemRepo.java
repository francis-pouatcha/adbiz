package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstBsnsItem;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstBsnsItemRepo<E extends CoreAbstBsnsItem> extends CoreAbstBsnsItemHeaderRepo<E>{

	public QueryResult<E> findByCntnrIdentifAndIdentifBetween(String cntnrIdentif, String identifStart, String identifEnd);
	
	public QueryResult<E> findByCntnrIdentifAndDisabledDtIsNull(String cntnrIdentif);  
	
	public QueryResult<E> findByCntnrIdentifAndSection(String cntnrIdentif, String section);

	public QueryResult<E> findByCntnrIdentifAndArtPic(String cntnrIdentif, String artPic);

	public QueryResult<E> findByCntnrIdentifAndArtPicAndLotPic(String cntnrIdentif, String artPic, String lotPic);
	
	public QueryResult<E> findByCntnrIdentifAndArtPicAndOuIdentif(String cntnrIdentif,String artPic, String ouIdentif);
	
	public QueryResult<E> findByCntnrIdentifAndArtPicAndSection(String cntnrIdentif, String artPic, String section);
	
	public QueryResult<E> findByCntnrIdentifAndSalIndex(String cntnrIdentif, String salIndex);
	
	public QueryResult<E> findByCntnrIdentifAndDisabledDtIsNotNull(String cntnrIdentif);  
	
	public QueryResult<E> findByCntnrIdentifAndSalIndexAndDisabledDtIsNull(String cntnrIdentif, String salIndex);  
	
	public QueryResult<E> findByCntnrIdentifAndSalIndexAndDisabledDtIsNotNull(String cntnrIdentif, String salIndex);  
	
	public QueryResult<E> findByCntnrIdentifAndConflictDtIsNotNull(String cntnrIdentif);
	
	public QueryResult<E> findByArtPic(String artPic);
	public QueryResult<E> findByArtPicAndExpirDt(String artPic, Date expirDt);

	public QueryResult<E> findByArtPicLike(String artPic);

	public QueryResult<E> findByLotPicLike(String lotPic);

	public QueryResult<E> findBySupplierPicLike(String supplierPic);

	public QueryResult<E> findByLotPicLikeOrArtPicLikeOrSupplierPicLike(String lotPic, String artPic, String supplierPic);

	public QueryResult<E> findByExpirDtBetween(Date fromDt, Date toDt);

	public QueryResult<E> findBySupplierAndExpirDtBetween(String supplier, Date fromDt, Date toDt);

	public QueryResult<E> findByArtPicAndSupplierPic(String artPic, String supplierPic);
	public QueryResult<E> findByArtPicAndSupplierPicAndExpirDt(String artPic, String supplierPic, Date expirDt);

	public QueryResult<E> findByArtPicAndSupplierPicAndManufacturerPic(String artPic, String supplierPic,
			String manufacturerPic);

	public QueryResult<E> findByArtPicAndManufacturerPic(String artPic, String manufacturerPic);
	public QueryResult<E> findByArtPicAndManufacturerPicAndExpirDt(String artPic, String manufacturerPic, Date expirDt);
	
	public QueryResult<E> findByArtPicAndSupplierPicAndManufacturerPicAndExpirDt(String artPic, String supplierPic,
			String manufacturerPic, Date expirDt);
	
}
