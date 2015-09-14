package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class OuWsRestrictionSearchResult extends CoreAbstIdentifObjectSearchResult<OuWsRestriction>
{

	public OuWsRestrictionSearchResult() {
		super();
	}

	public OuWsRestrictionSearchResult(Long count,
			List<OuWsRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<OuWsRestriction> searchInput) {
		super(count, resultList, searchInput);
	}

	public OuWsRestrictionSearchResult(Long count, Long total,
			List<OuWsRestriction> resultList,
			CoreSearchInput<OuWsRestriction> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
