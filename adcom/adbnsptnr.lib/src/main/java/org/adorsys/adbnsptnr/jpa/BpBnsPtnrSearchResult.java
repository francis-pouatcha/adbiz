package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpBnsPtnrSearchResult extends CoreAbstIdentifObjectSearchResult<BpBnsPtnr>
{

	public BpBnsPtnrSearchResult() {
		super();
	}

	public BpBnsPtnrSearchResult(Long count, List<BpBnsPtnr> resultList,
			CoreAbstIdentifObjectSearchInput<BpBnsPtnr> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpBnsPtnrSearchResult(Long count, Long total, List<BpBnsPtnr> resultList,
			CoreSearchInput<BpBnsPtnr> searchInput) {
		super(count, total, resultList, searchInput);
	}
}