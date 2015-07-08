package org.adorsys.adstock.repo;

import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends EntityRepository<StkLotStockQty, String>
{
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1")
	public List<StkLotStockQty> findByArtPic(String artPic);

	@Query("SELECT DISTINCT e.lotPic FROM StkLotStockQty AS e WHERE e.artPic = ?1")
	public QueryResult<String> findLotPicByArtPic(String artPic);
	
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPic(String artPic,String lotPic);

	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.cnsldtd=?3")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndCnsldtd(String artPic,String lotPic,Boolean cnsldtd);

	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.section=?3")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSection(String artPic,String lotPic, String section);

	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.section=?3 AND e.cnsldtd=?4")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSectionAndCnsldtd(String artPic,String lotPic,String section,Boolean cnsldtd);
	
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.section=?3 AND e.seqNbr >= ?4")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSectionAndSeq(String artPic,String lotPic, String section, int seqNbr);
	
	@Query("SELECT SUM(e.stockQty) FROM StkLotStockQty AS e WHERE e.artPic = ?1 ")
	public BigDecimal countStockByArtPic(String artPic);
	
	@Query("SELECT c FROM StkLotStockQty AS c GROUP BY c.artPic, c.id ORDER BY SUM(c.stockQty) ASC")
	public QueryResult<StkLotStockQty> itemsShortage();
}
