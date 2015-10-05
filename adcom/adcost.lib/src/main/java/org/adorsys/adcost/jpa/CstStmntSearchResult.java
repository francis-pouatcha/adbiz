package org.adorsys.adcost.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

public class CstStmntSearchResult extends CoreAbstBsnsObjectSearchResult<CstStmnt>{

	public CstStmntSearchResult() {
		super();
	}

	public CstStmntSearchResult(Long count, Long total,
			List<CstStmnt> resultList,
			CoreAbstBsnsObjectSearchInput<CstStmnt> searchInput) {
		super(count, total, resultList, searchInput);
	}

	public CstStmntSearchResult(Long count, List<CstStmnt> resultList,
			CoreAbstIdentifObjectSearchInput<CstStmnt> searchInput) {
		super(count, resultList, searchInput);
	}

}
