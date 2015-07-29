package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArt2ProductFamily.class)
public interface CatalArt2ProductFamilyRepository extends CoreAbstIdentifRepo<CatalArt2ProductFamily>
{
	public QueryResult<CatalArt2ProductFamily> findByArtPic(String artPic);
}
