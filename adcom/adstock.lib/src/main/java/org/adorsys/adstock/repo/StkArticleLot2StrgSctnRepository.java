package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2StrgSctn.class)
public interface StkArticleLot2StrgSctnRepository extends CoreAbstIdentifDataRepo<StkArticleLot2StrgSctn>
{
	public QueryResult<StkArticleLot2StrgSctn> findByLotPic(String lotPic);
	
	public QueryResult<StkArticleLot2StrgSctn> findByStrgSection(String strgSection);
}
