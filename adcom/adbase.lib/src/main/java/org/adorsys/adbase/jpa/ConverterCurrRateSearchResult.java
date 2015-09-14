package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class ConverterCurrRateSearchResult extends CoreAbstIdentifObjectSearchResult<ConverterCurrRate>
{
	public ConverterCurrRateSearchResult() {
		super();
	}

	public ConverterCurrRateSearchResult(Long count,
			List<ConverterCurrRate> resultList,
			CoreAbstIdentifObjectSearchInput<ConverterCurrRate> searchInput) {
		super(count, resultList, searchInput);
	}

	public ConverterCurrRateSearchResult(Long count, Long total,
			List<ConverterCurrRate> resultList,
			CoreSearchInput<ConverterCurrRate> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
