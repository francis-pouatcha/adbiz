package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class StkSectionSearchResult extends CoreAbstIdentifObjectSearchResult<StkSection>
{

	public StkSectionSearchResult() {
		super();
	}

	public StkSectionSearchResult(Long count, List<StkSection> resultList,
			CoreAbstIdentifObjectSearchInput<StkSection> searchInput) {
		super(count, resultList, searchInput);
	}

	public StkSectionSearchResult(Long count, Long total,
			List<StkSection> resultList, CoreSearchInput<StkSection> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
	
}
