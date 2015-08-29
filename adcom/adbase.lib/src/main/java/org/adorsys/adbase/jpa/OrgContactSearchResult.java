package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class OrgContactSearchResult extends CoreAbstIdentifObjectSearchResult<OrgContact>
{

	public OrgContactSearchResult() {
		super();
	}

	public OrgContactSearchResult(Long count, List<OrgContact> resultList,
			CoreAbstIdentifObjectSearchInput<OrgContact> searchInput) {
		super(count, resultList, searchInput);
	}

	public OrgContactSearchResult(Long count, Long total,
			List<OrgContact> resultList, CoreSearchInput<OrgContact> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
