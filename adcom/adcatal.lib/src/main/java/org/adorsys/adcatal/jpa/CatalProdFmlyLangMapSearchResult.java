package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalProdFmlyLangMapSearchResult extends
		CoreAbstIdentifObjectSearchResult<CatalProdFmlyLangMap> {

	public CatalProdFmlyLangMapSearchResult() {
		super();
	}

	public CatalProdFmlyLangMapSearchResult(Long count, Long total,
			List<CatalProdFmlyLangMap> resultList,
			CoreAbstIdentifObjectSearchInput<CatalProdFmlyLangMap> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
