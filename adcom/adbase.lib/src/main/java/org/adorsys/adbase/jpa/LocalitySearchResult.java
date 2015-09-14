package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class LocalitySearchResult extends CoreAbstIdentifObjectSearchResult<Locality>
{
	public LocalitySearchResult() {
		super();
	}

	public LocalitySearchResult(Long count, List<Locality> resultList,
			CoreAbstIdentifObjectSearchInput<Locality> searchInput) {
		super(count, resultList, searchInput);
	}

	public LocalitySearchResult(Long count, Long total,
			List<Locality> resultList, CoreSearchInput<Locality> searchInput) {
		super(count, total, resultList, searchInput);
	}

}
