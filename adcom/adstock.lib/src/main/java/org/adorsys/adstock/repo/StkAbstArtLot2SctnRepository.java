package org.adorsys.adstock.repo;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkAbstLot2Section;
import org.apache.deltaspike.data.api.QueryResult;

public interface StkAbstArtLot2SctnRepository<E extends StkAbstLot2Section> extends CoreAbstIdentifRepo<E>
{
	public QueryResult<E> findByLotPic(String lotPic);
	
	public QueryResult<E> findBySection(String section);
	
	public QueryResult<E> findByClosedDtIsNull();
	
}
