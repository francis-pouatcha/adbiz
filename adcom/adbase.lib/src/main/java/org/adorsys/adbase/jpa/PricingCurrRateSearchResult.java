package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PricingCurrRateSearchResult extends
		CoreAbstIdentifObjectSearchResult<PricingCurrRate> {

	public PricingCurrRateSearchResult() {
		super();
	}

	public PricingCurrRateSearchResult(Long count,
			List<PricingCurrRate> resultList,
			CoreAbstIdentifObjectSearchInput<PricingCurrRate> searchInput) {
		super(count, resultList, searchInput);
	}

	public PricingCurrRateSearchResult(Long count, Long total,
			List<PricingCurrRate> resultList,
			CoreSearchInput<PricingCurrRate> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
