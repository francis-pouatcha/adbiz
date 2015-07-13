package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkSectionSearchResult extends CoreAbstIdentifObjectSearchResult<StkSection, CoreAbstIdentifObjectSearchInput<StkSection>>
{

	public StkSectionSearchResult() {
		super();
	}

	public StkSectionSearchResult(Long count, List<StkSection> resultList,
			CoreAbstIdentifObjectSearchInput<StkSection> searchInput) {
		super(count, resultList, searchInput);
	}
}
