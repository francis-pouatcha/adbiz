package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PermEntrySearchResult extends CoreAbstIdentifObjectSearchResult<PermEntry>
{

	public PermEntrySearchResult() {
		super();
	}

	public PermEntrySearchResult(Long count, List<PermEntry> resultList,
			CoreAbstIdentifObjectSearchInput<PermEntry> searchInput) {
		super(count, resultList, searchInput);
	}

	public PermEntrySearchResult(Long count, Long total,
			List<PermEntry> resultList, CoreSearchInput<PermEntry> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
