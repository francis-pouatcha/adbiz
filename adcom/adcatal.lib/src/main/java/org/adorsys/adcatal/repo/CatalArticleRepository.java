package org.adorsys.adcatal.repo;


import org.adorsys.adcatal.jpa.CatalArticle;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArticle.class)
public interface CatalArticleRepository extends CatalAbstractArticleRepository<CatalArticle>{
	
	@Query("SELECT art,lang FROM CatalArticle AS art, CatalArtLangMapping AS lang WHERE art.identif = lang.cntnrIdentif AND LOWER(?1) LIKE LOWER(lang.artName)")
	public QueryResult<CatalArticle> findByArtName(String artName);
	
	@Query("SELECT COUNT(*) FROM CatalArticle AS art, CatalArtLangMapping AS lang WHERE art.identif = lang.cntnrIdentif AND  LOWER(?1) LIKE LOWER(lang.artName)" )
	public Long countByArtName(String artName);
}
