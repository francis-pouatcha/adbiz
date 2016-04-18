package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

@XmlRootElement
public class CdrDrctSalesSearchResult extends CoreAbstBsnsObjectSearchResult<CdrDrctSales>
{

	public CdrDrctSalesSearchResult() {
		super();
	}

	public CdrDrctSalesSearchResult(Long count, List<CdrDrctSales> resultList,
			CoreAbstIdentifObjectSearchInput<CdrDrctSales> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrDrctSalesSearchResult(Long count, Long total, List<CdrDrctSales> resultList,
			CoreAbstBsnsObjectSearchInput<CdrDrctSales> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
