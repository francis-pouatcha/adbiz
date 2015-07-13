package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends CoreAbstIdentifDataRepo<StkLotStockQty>
{
	public QueryResult<StkLotStockQty> findByArtPic(String artPic);

	public QueryResult<StkLotStockQty> findByArtPicAndLotPic(String artPic,String lotPic);

	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndCnsldtd(String artPic,String lotPic,Boolean cnsldtd);

	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSection(String artPic,String lotPic, String section);

	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSectionAndCnsldtd(String artPic,String lotPic,String section,Boolean cnsldtd);
	
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSectionAndSeqNbr(String artPic,String lotPic, String section, int seqNbr);
}
