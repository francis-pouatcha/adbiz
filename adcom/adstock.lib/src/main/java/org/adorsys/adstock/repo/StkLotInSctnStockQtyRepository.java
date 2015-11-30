package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotInSctnStockQty.class)
public interface StkLotInSctnStockQtyRepository extends CoreAbstIdentifRepo<StkLotInSctnStockQty>
{
	public QueryResult<StkLotInSctnStockQty> findByCntnrIdentifAndLotPic(String cntnrIdentif,String lotPic);

	public QueryResult<StkLotInSctnStockQty> findByCntnrIdentifAndLotPicAndCnsldtd(String cntnrIdentif,String lotPic,Boolean cnsldtd);

	public QueryResult<StkLotInSctnStockQty> findByCntnrIdentifAndLotPicAndSection(String cntnrIdentif,String lotPic, String section);

	public QueryResult<StkLotInSctnStockQty> findByCntnrIdentifAndLotPicAndSectionAndCnsldtd(String cntnrIdentif,String lotPic,String section,Boolean cnsldtd);
	
	public QueryResult<StkLotInSctnStockQty> findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(String cntnrIdentif,String lotPic, String section, int seqNbr);
}
