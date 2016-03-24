package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.deltaspike.data.api.EntityManagerDelegate;
import org.apache.deltaspike.data.api.FullEntityRepository;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstIdentifRepo<E extends CoreAbstIdentifObject> extends FullEntityRepository<E, String>, EntityManagerDelegate<E>{
	
	public E findOptionalByIdentif(String identif);
	
	public QueryResult<E> findByIdentif(String identif);

	public QueryResult<E> findByIdentifBetween(String identifStart, String identifEnd);
	
	public QueryResult<E> findByCntnrIdentif(String cntnrIdentif);
	
	public QueryResult<E> findByValueDtBetween(Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByCntnrIdentifAndValueDtBetween(String cntnrIdentif, Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByIdentifGreaterThan(String idStart);

	public QueryResult<E> findByIdentifGreaterThanEquals(
			String idStart);

	public QueryResult<E> findByIdentifLessThan(String idEnd);

	public QueryResult<E> findByIdentifLessThanEquals(
			String idEnd);

	public QueryResult<E> findByCntnrIdentifBetween(
			String identifStart, String identifEnd);

	public QueryResult<E> findByCntnrIdentifGreaterThan(
			String identifStart);

	public QueryResult<E> findByCntnrIdentifGreaterThanEquals(
			String identifStart);

	public QueryResult<E> findByCntnrIdentifLessThan(
			String identifEnd);

	public QueryResult<E> findByCntnrIdentifLessThanEquals(
			String identifEnd);
}
