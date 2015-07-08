package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkLotRsvQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotRsvQty.class)
public interface StkLotRsvQtyRepository extends EntityRepository<StkLotRsvQty, String>
{
	@Query("SELECT e FROM StkLotRsvQty AS e WHERE e.artPic = ?1")
	public List<StkLotRsvQty> findByArtPic(String artPic);
	
	@Query("SELECT e FROM StkLotRsvQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2")
	public QueryResult<StkLotRsvQty> findByArtPicAndLotPic(String artPic,String lotPic);

	@Query("SELECT e FROM StkLotRsvQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.section=?3")
	public QueryResult<StkLotRsvQty> findByArtPicAndLotPicAndSection(String artPic,String lotPic, String section);

	@Query("SELECT e FROM StkLotRsvQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.sesction=?3 AND e.cnsldtd=?4")
	public QueryResult<StkLotRsvQty> findByArtPicAndLotPicAndSectionAndCnsldtd(String artPic,String lotPic,String sesction,Boolean cnsldtd);
	
	@Query("SELECT e FROM StkLotRsvQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.sesction=?3 AND e.seqNbr >= ?4")
	public QueryResult<StkLotRsvQty> findByArtPicAndLotPicAndSectionAndSeq(String artPic,String lotPic, String sesction, int seqNbr);
}
