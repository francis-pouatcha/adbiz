package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class RoleEntrySearchResult extends CoreAbstIdentifObjectSearchResult<RoleEntry>
{

	public RoleEntrySearchResult() {
		super();
	}

	public RoleEntrySearchResult(Long count, List<RoleEntry> resultList,
			CoreAbstIdentifObjectSearchInput<RoleEntry> searchInput) {
		super(count, resultList, searchInput);
	}

	public RoleEntrySearchResult(Long count, Long total,
			List<RoleEntry> resultList, CoreSearchInput<RoleEntry> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
