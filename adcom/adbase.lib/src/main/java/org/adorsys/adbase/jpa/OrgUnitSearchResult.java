package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class OrgUnitSearchResult extends CoreAbstIdentifObjectSearchResult<OrgUnit>
{

	public OrgUnitSearchResult() {
		super();
	}

	public OrgUnitSearchResult(Long count, List<OrgUnit> resultList,
			CoreAbstIdentifObjectSearchInput<OrgUnit> searchInput) {
		super(count, resultList, searchInput);
	}

	public OrgUnitSearchResult(Long count, Long total,
			List<OrgUnit> resultList, CoreSearchInput<OrgUnit> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
