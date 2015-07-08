package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHeader;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstBsnsObjectHeaderRepo<E extends CoreAbstBsnsObjectHeader> extends CoreAbstIdentifDataRepo<E>{

	public QueryResult<E> findByOrgIdentif(String orgIdentif);
	
	public QueryResult<E> findByOuIdentif(String ouIdentif);

	public QueryResult<E> findByOrgIdentifAndOuIdentif(String orgIdentif, String ouIdentif);

	public QueryResult<E> findByCntnrIdentifAndOrgIdentif(String cntnrIdentif, String orgIdentif);
	
	public QueryResult<E> findByCntnrIdentifAndOuIdentif(String cntnrIdentif, String ouIdentif);

	public QueryResult<E> findByCntnrIdentifAndOrgIdentifAndOuIdentif(String cntnrIdentif, String orgIdentif, String ouIdentif);

	public QueryResult<E> findByOrgIdentifAndValueDtBetween(String orgIdentif, Date valueDtFrom, Date valueDtTo);
	
	public QueryResult<E> findByOuIdentifAndValueDtBetween(String ouIdentif, Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByOrgIdentifAndOuIdentifAndValueDtBetween(String orgIdentif, String ouIdentif, Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByCntnrIdentifAndOrgIdentifAndValueDtBetween(String cntnrIdentif, String orgIdentif, Date valueDtFrom, Date valueDtTo);
	
	public QueryResult<E> findByCntnrIdentifAndOuIdentifAndValueDtBetween(String cntnrIdentif, String ouIdentif, Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByCntnrIdentifAndOrgIdentifAndOuIdentifAndValueDtBetween(String cntnrIdentif, String orgIdentif, String ouIdentif, Date valueDtFrom, Date valueDtTo);
}
