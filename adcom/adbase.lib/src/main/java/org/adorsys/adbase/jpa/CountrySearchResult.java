package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CountrySearchResult extends CoreAbstIdentifObjectSearchResult<Country>
{
	public CountrySearchResult() {
		super();
	}

	public CountrySearchResult(Long count,
			List<Country> resultList,
			CoreAbstIdentifObjectSearchInput<Country> searchInput) {
		super(count, resultList, searchInput);
	}

	public CountrySearchResult(Long count, Long total,
			List<Country> resultList,
			CoreSearchInput<Country> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
