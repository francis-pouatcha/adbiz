package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstIdentifDataRepo<E extends CoreAbstIdentifObject> extends EntityRepository<E, String>{
	
	public E findOptionalByIdentif(String identif);
	
	public QueryResult<E> findByIdentif(String identif);

	public QueryResult<E> findByIdentifBetween(String identifStart, String identifEnd);
	
	public QueryResult<E> findByCntnrIdentif(String cntnrIdentif);
	
	public QueryResult<E> findByValueDtBetween(Date valueDtFrom, Date valueDtTo);

	public QueryResult<E> findByCntnrIdentifAndValueDtBetween(String cntnrIdentif, Date valueDtFrom, Date valueDtTo);
}
