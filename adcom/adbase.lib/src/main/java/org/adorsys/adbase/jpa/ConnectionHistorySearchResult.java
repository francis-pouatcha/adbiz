package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class ConnectionHistorySearchResult extends CoreAbstIdentifObjectSearchResult<ConnectionHistory>
{
	public ConnectionHistorySearchResult() {
		super();
	}

	public ConnectionHistorySearchResult(Long count,
			List<ConnectionHistory> resultList,
			CoreAbstIdentifObjectSearchInput<ConnectionHistory> searchInput) {
		super(count, resultList, searchInput);
	}

	public ConnectionHistorySearchResult(Long count, Long total,
			List<ConnectionHistory> resultList,
			CoreSearchInput<ConnectionHistory> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
