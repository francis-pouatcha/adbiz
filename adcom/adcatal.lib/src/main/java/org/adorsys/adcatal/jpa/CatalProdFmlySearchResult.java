package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalProdFmlySearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalProdFmly> {

	public CatalProdFmlySearchResult() {
		super();
	}

	public CatalProdFmlySearchResult(Long count,
			List<CatalProdFmly> resultList,
			CoreAbstIdentifObjectSearchInput<CatalProdFmly> searchInput) {
		super(count, resultList, searchInput);
	}
}
