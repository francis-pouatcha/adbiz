package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class OuTypeSearchResult extends CoreAbstIdentifObjectSearchResult<OuType>
{
	public OuTypeSearchResult() {
		super();
	}

	public OuTypeSearchResult(Long count, List<OuType> resultList,
			CoreAbstIdentifObjectSearchInput<OuType> searchInput) {
		super(count, resultList, searchInput);
	}

	public OuTypeSearchResult(Long count, Long total, List<OuType> resultList,
			CoreSearchInput<OuType> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
