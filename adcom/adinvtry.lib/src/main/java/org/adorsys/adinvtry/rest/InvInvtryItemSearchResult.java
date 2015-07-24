package org.adorsys.adinvtry.rest;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adinvtry.jpa.InvInvtryItem;

public class InvInvtryItemSearchResult extends CoreAbstBsnsItemSearchResult<InvInvtryItem>{

	public InvInvtryItemSearchResult() {
		super();
	}

	public InvInvtryItemSearchResult(Long count,
			List<InvInvtryItem> resultList,
			CoreAbstBsnsItemSearchInput<InvInvtryItem> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
