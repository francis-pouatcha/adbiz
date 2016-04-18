package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpPtnrCtgrySearchResult extends CoreAbstIdentifObjectSearchResult<BpPtnrCtgry> {

	public BpPtnrCtgrySearchResult() {
		super();
	}

	public BpPtnrCtgrySearchResult(Long count, List<BpPtnrCtgry> resultList,
			CoreAbstIdentifObjectSearchInput<BpPtnrCtgry> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpPtnrCtgrySearchResult(Long count, Long total, List<BpPtnrCtgry> resultList,
			CoreSearchInput<BpPtnrCtgry> searchInput) {
		super(count, total, resultList, searchInput);
	}
}