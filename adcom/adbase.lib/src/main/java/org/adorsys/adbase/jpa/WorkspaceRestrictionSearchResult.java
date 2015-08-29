package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class WorkspaceRestrictionSearchResult extends CoreAbstIdentifObjectSearchResult<WorkspaceRestriction>
{

	public WorkspaceRestrictionSearchResult() {
		super();
	}

	public WorkspaceRestrictionSearchResult(Long count,
			List<WorkspaceRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<WorkspaceRestriction> searchInput) {
		super(count, resultList, searchInput);
	}

	public WorkspaceRestrictionSearchResult(Long count, Long total,
			List<WorkspaceRestriction> resultList,
			CoreSearchInput<WorkspaceRestriction> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
