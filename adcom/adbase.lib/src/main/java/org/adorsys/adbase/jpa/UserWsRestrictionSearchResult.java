package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class UserWsRestrictionSearchResult extends CoreAbstIdentifObjectSearchResult<UserWsRestriction>
{

	public UserWsRestrictionSearchResult() {
		super();
	}

	public UserWsRestrictionSearchResult(Long count,
			List<UserWsRestriction> resultList,
			CoreAbstIdentifObjectSearchInput<UserWsRestriction> searchInput) {
		super(count, resultList, searchInput);
	}

	public UserWsRestrictionSearchResult(Long count, Long total,
			List<UserWsRestriction> resultList,
			CoreSearchInput<UserWsRestriction> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
