package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2StrgSctn.class)
public interface StkArticleLot2StrgSctnRepository extends EntityRepository<StkArticleLot2StrgSctn, String>
{
	public List<StkArticleLot2StrgSctn> findByArtPicAndLotPic(String artPic, String lotPic);
	
	@Query("SELECT e FROM StkArticleLot2StrgSctn AS e WHERE LOWER(e.artPic) LIKE(LOWER(?1)) AND LOWER(e.strgSection) LIKE(LOWER(?2))")
	public List<StkArticleLot2StrgSctn> findByArtPicAndStrgSection(String artPic, String sec);
	
	@Query("SELECT e FROM StkArticleLot2StrgSctn AS e WHERE LOWER(e.strgSection) LIKE(LOWER(?1))")
	public QueryResult<StkArticleLot2StrgSctn> findByStrgSection(String sec);

	@Query("SELECT e FROM StkArticleLot2StrgSctn AS e WHERE LOWER(e.strgSection)=LOWER(?1) AND LOWER(SUBSTRING(e.artName, 1, 1))>=LOWER(?2) AND LOWER(SUBSTRING(e.artName, 1, 1))<=LOWER(?3)")
	public QueryResult<StkArticleLot2StrgSctn> findByStrgSectionAndArtNameRange(String sec, String startRange, String endRange);

	@Query("SELECT e FROM StkArticleLot2StrgSctn AS e WHERE e.artPic=?1")
	public QueryResult<StkArticleLot2StrgSctn> findByArtPic(String artPic);

	@Query("SELECT count(e.id) FROM StkArticleLot2StrgSctn AS e WHERE LOWER(e.strgSection) LIKE LOWER(?1)")
	public Long countByStrgSection(String strgSection);

	@Query("SELECT count(e.id) FROM StkArticleLot2StrgSctn AS e WHERE LOWER(e.strgSection)=LOWER(?1) AND LOWER(SUBSTRING(e.artName, 1, 1))>=LOWER(?2) AND LOWER(SUBSTRING(e.artName, 1, 1))<=LOWER(?3)")
	public Long countByStrgSectionAndArtNameRange(String sec, String startRange, String endRange);

	@Query("SELECT e FROM StkArticleLot2StrgSctn AS e WHERE LOWER(SUBSTRING(e.artName, 1, 1))>=LOWER(?1) AND LOWER(SUBSTRING(e.artName, 1, 1))<=LOWER(?2)")
	public QueryResult<StkArticleLot2StrgSctn> findByArtNameRange(String rangeStart, String rangeEnd);

	@Query("SELECT count(e.id) FROM StkArticleLot2StrgSctn AS e WHERE LOWER(SUBSTRING(e.artName, 1, 1))>=LOWER(?1) AND LOWER(SUBSTRING(e.artName, 1, 1))<=LOWER(?2)")
	public Long countByArtNameRange(String rangeStart, String rangeEnd);
}
