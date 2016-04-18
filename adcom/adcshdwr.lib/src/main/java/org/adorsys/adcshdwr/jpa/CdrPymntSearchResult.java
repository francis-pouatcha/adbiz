package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CdrPymntSearchResult extends CoreAbstIdentifObjectSearchResult<CdrPymnt>{

	public CdrPymntSearchResult() {
		super();
	}

	public CdrPymntSearchResult(Long count, List<CdrPymnt> resultList,
			CoreAbstIdentifObjectSearchInput<CdrPymnt> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrPymntSearchResult(Long count, Long total, List<CdrPymnt> resultList,
			CoreSearchInput<CdrPymnt> searchInput) {
		super(count, total, resultList, searchInput);
	}
}