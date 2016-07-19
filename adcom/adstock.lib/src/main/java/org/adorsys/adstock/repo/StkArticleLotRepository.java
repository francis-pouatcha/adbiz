package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot.class)
public interface StkArticleLotRepository extends CoreAbstBsnsItemRepo<StkArticleLot>{
	
	@Query("SELECT stk from CatalArtLangMapping AS catal, StkArticleLot AS stk WHERE LOWER(catal.artName) LIKE LOWER(?1) AND catal.cntnrIdentif = stk.artPic ORDER BY catal.artName ASC")
	public QueryResult<StkArticleLot> findStkArticeLotByArtName(String artName);
}
