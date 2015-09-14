package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class UserWorkspaceSearchResult extends CoreAbstIdentifObjectSearchResult<UserWorkspace>
{

	public UserWorkspaceSearchResult() {
		super();
	}

	public UserWorkspaceSearchResult(Long count,
			List<UserWorkspace> resultList,
			CoreAbstIdentifObjectSearchInput<UserWorkspace> searchInput) {
		super(count, resultList, searchInput);
	}

	public UserWorkspaceSearchResult(Long count, Long total,
			List<UserWorkspace> resultList,
			CoreSearchInput<UserWorkspace> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
