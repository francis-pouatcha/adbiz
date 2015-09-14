package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class WorkspaceSearchResult extends CoreAbstIdentifObjectSearchResult<Workspace>
{

	public WorkspaceSearchResult() {
		super();
	}

	public WorkspaceSearchResult(Long count, List<Workspace> resultList,
			CoreAbstIdentifObjectSearchInput<Workspace> searchInput) {
		super(count, resultList, searchInput);
	}

	public WorkspaceSearchResult(Long count, Long total,
			List<Workspace> resultList, CoreSearchInput<Workspace> searchInput) {
		super(count, total, resultList, searchInput);
	}

}
