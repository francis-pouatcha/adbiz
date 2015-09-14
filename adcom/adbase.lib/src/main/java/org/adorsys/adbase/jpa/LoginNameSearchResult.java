package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class LoginNameSearchResult extends
		CoreAbstIdentifObjectSearchResult<LoginName> {

	public LoginNameSearchResult() {
		super();
	}

	public LoginNameSearchResult(Long count, List<LoginName> resultList,
			CoreAbstIdentifObjectSearchInput<LoginName> searchInput) {
		super(count, resultList, searchInput);
	}

	public LoginNameSearchResult(Long count, Long total,
			List<LoginName> resultList, CoreSearchInput<LoginName> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
