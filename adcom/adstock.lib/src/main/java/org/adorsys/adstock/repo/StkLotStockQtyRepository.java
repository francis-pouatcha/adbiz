package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends CoreAbstIdentifRepo<StkLotStockQty>
{
	public QueryResult<StkLotStockQty> findByCntnrIdentifAndLotPic(String cntnrIdentif,String lotPic);

	public QueryResult<StkLotStockQty> findByCntnrIdentifAndLotPicAndCnsldtd(String cntnrIdentif,String lotPic,Boolean cnsldtd);

	public QueryResult<StkLotStockQty> findByCntnrIdentifAndLotPicAndSeqNbr(String cntnrIdentif,String lotPic, int seqNbr);

}
