package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CdrPymntItemSearchResult extends CoreAbstIdentifObjectSearchResult<CdrPymntItem>{

	public CdrPymntItemSearchResult() {
		super();
	}

	public CdrPymntItemSearchResult(Long count, List<CdrPymntItem> resultList,
			CoreAbstIdentifObjectSearchInput<CdrPymntItem> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrPymntItemSearchResult(Long count, Long total, List<CdrPymntItem> resultList,
			CoreSearchInput<CdrPymntItem> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
