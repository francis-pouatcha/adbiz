package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2Ou.class)
public interface StkArticleLot2OuRepository extends CoreAbstIdentifDataRepo<StkArticleLot2Ou>
{
	public QueryResult<StkArticleLot2Ou> findByArtPicAndLotPic(String artPic, String lotPic);
}
