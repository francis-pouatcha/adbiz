package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstBsnsObjectRepo<E extends CoreAbstBsnsObject> extends CoreAbstBsnsObjectHeaderRepo<E>{

	public QueryResult<E> findByTxDtBetween(Date from, Date to);

	public QueryResult<E> findByPreparedDtIsNull();

	// With txDt
	public QueryResult<E> findByOrgIdentifAndTxDtBetween(String orgIdentif,Date from, Date to);
	
	public QueryResult<E> findByOuIdentifAndTxDtBetween(String ouIdentif, Date from, Date to);
	
	public QueryResult<E> findByCntnrIdentifAndTxDtBetween(String cntnrIdentif, Date from, Date to);

	public QueryResult<E> findByCntnrIdentifAndOuIdentifAndTxDtBetween(String cntnrIdentif, String ouIdentif, Date from, Date to);

	public QueryResult<E> findByCntnrIdentifAndOuIdentifAndTxStatusAndTxDtBetween(String cntnrIdentif, String ouIdentif, String status, Date from, Date to);

	public QueryResult<E> findByCntnrIdentifIsNotNullAndMergedDateIsNull();

	public QueryResult<E> findByCntnrIdentifIsNotNullAndMergedDateIsNotNull();
}
