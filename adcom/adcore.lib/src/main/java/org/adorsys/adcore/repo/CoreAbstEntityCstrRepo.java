package org.adorsys.adcore.repo;

import java.util.Date;

import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstEntityCstrRepo<E extends CoreAbstEntityCstr> extends CoreAbstIdentifDataRepo<E>{
	public QueryResult<E> findByEntIdentif(String entIdentif);
	public QueryResult<E> findByEntIdentifAndCstrType(String entIdentif, String cstrType);
	public QueryResult<E> findByCstrType(String cstrType);
	public QueryResult<E> findByCstrTypeAndValidTo(String cstrType, Date validTo);
	public QueryResult<E> findByEntIdentifCstrTypeAndValidTo(String entIdentif, String cstrType, Date validTo);
}
