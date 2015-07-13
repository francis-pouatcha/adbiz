package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectHstry;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstBsnsObjHstryRepo<E extends CoreAbstBsnsObjectHstry> extends CoreAbstIdentifObjectHstryRepo<E>{

	public QueryResult<E> findByEntIdentifAndIdBetween(String entIdentif, String idStart, String idEnd);
}
