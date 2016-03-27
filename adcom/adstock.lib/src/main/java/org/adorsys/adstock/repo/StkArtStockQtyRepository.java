package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArtStockQty.class)
public interface StkArtStockQtyRepository extends CoreAbstIdentifRepo<StkArtStockQty>
{
	public QueryResult<StkArtStockQty> findByCntnrIdentifAndCnsldtd(String cntnrIdentif, Boolean cnsldtd);

	public QueryResult<StkArtStockQty> findByCntnrIdentifAndSeqNbr(String cntnrIdentif, int seqNbr);
}
