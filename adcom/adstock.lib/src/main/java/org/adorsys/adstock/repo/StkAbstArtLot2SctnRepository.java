package org.adorsys.adstock.repo;

import java.util.Date;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adstock.jpa.StkAbstLot2Section;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.apache.deltaspike.data.api.QueryResult;

public interface StkAbstArtLot2SctnRepository<E extends StkAbstLot2Section> extends CoreAbstIdentifRepo<E>
{
	public QueryResult<E> findByLotPic(String lotPic);
	
	public QueryResult<E> findBySection(String section);
	
	public QueryResult<E> findByClosedDtIsNull();
	
	public QueryResult<E> findBySectionAndClosedDtIsNull(String section);
	
	public QueryResult<E> findByArtPicAndSection(String artPic, String section);	

	public QueryResult<E> findByArtPic(String artPic);
	
	public QueryResult<StkArticleLot2StrgSctn> findByArtPicAndExpitDt(String artPic, Date expirDt);
}
