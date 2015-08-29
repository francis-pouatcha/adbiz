package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class SecTerminalSearchResult extends CoreAbstIdentifObjectSearchResult<SecTerminal>
{

	public SecTerminalSearchResult() {
		super();
	}

	public SecTerminalSearchResult(Long count, List<SecTerminal> resultList,
			CoreAbstIdentifObjectSearchInput<SecTerminal> searchInput) {
		super(count, resultList, searchInput);
	}

	public SecTerminalSearchResult(Long count, Long total,
			List<SecTerminal> resultList,
			CoreSearchInput<SecTerminal> searchInput) {
		super(count, total, resultList, searchInput);
	}

}
