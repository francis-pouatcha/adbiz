package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CdrCstmrVchrSearchResult extends CoreAbstIdentifObjectSearchResult<CdrCstmrVchr> {

	public CdrCstmrVchrSearchResult() {
		super();
	}

	public CdrCstmrVchrSearchResult(Long count, List<CdrCstmrVchr> resultList,
			CoreAbstIdentifObjectSearchInput<CdrCstmrVchr> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrCstmrVchrSearchResult(Long count, Long total, List<CdrCstmrVchr> resultList,
			CoreSearchInput<CdrCstmrVchr> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
