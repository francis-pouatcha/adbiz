package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CdrDrctSalesItemSearchResult extends CoreAbstBsnsItemSearchResult<CdrDrctSalesItem>{

	public CdrDrctSalesItemSearchResult() {
		super();
	}

	public CdrDrctSalesItemSearchResult(Long count, List<CdrDrctSalesItem> resultList,
			CoreAbstBsnsItemSearchInput<CdrDrctSalesItem> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrDrctSalesItemSearchResult(Long count, Long total, List<CdrDrctSalesItem> resultList,
			CoreSearchInput<CdrDrctSalesItem> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
