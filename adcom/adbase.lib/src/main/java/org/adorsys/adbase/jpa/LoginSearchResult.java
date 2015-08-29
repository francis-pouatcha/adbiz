package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class LoginSearchResult extends CoreAbstIdentifObjectSearchResult<Login> {

	public LoginSearchResult() {
		super();
	}

	public LoginSearchResult(Long count, List<Login> resultList,
			CoreAbstIdentifObjectSearchInput<Login> searchInput) {
		super(count, resultList, searchInput);
	}

	public LoginSearchResult(Long count, Long total, List<Login> resultList,
			CoreSearchInput<Login> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
