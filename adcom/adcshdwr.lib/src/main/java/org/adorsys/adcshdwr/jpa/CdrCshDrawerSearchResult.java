package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class CdrCshDrawerSearchResult extends CoreAbstIdentifObjectSearchResult<CdrCshDrawer>{

	public CdrCshDrawerSearchResult() {
		super();
	}

	public CdrCshDrawerSearchResult(Long count, List<CdrCshDrawer> resultList,
			CoreAbstIdentifObjectSearchInput<CdrCshDrawer> searchInput) {
		super(count, resultList, searchInput);
	}

	public CdrCshDrawerSearchResult(Long count, Long total, List<CdrCshDrawer> resultList,
			CoreSearchInput<CdrCshDrawer> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
