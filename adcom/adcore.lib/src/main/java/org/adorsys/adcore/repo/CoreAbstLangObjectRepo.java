package org.adorsys.adcore.repo;

import org.adorsys.adcore.jpa.CoreAbstLangObject;
import org.apache.deltaspike.data.api.QueryResult;

public interface CoreAbstLangObjectRepo<E extends CoreAbstLangObject> extends CoreAbstIdentifDataRepo<E>{
	public QueryResult<E> findByCntnrIdentifAndLangIso2(String cntnrIdentif, String langIso2);
}
