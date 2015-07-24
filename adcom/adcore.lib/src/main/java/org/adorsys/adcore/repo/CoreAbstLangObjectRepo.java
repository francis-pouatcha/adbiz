package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstLangObject;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstLangObjectRepo<E extends CoreAbstLangObject> extends CoreAbstIdentifRepo<E>{
	public QueryResult<E> findByCntnrIdentifAndLangIso2(String cntnrIdentif, String langIso2);
}
