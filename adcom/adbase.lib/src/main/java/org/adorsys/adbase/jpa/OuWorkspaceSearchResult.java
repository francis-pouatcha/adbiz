package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class OuWorkspaceSearchResult extends CoreAbstIdentifObjectSearchResult<OuWorkspace>
{

	public OuWorkspaceSearchResult() {
		super();
	}

	public OuWorkspaceSearchResult(Long count, List<OuWorkspace> resultList,
			CoreAbstIdentifObjectSearchInput<OuWorkspace> searchInput) {
		super(count, resultList, searchInput);
	}

	public OuWorkspaceSearchResult(Long count, Long total,
			List<OuWorkspace> resultList,
			CoreSearchInput<OuWorkspace> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
