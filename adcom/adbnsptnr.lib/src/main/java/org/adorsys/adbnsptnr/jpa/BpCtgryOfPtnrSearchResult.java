package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpCtgryOfPtnrSearchResult extends CoreAbstIdentifObjectSearchResult<BpCtgryOfPtnr> {

	public BpCtgryOfPtnrSearchResult() {
		super();
	}

	public BpCtgryOfPtnrSearchResult(Long count, List<BpCtgryOfPtnr> resultList,
			CoreAbstIdentifObjectSearchInput<BpCtgryOfPtnr> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpCtgryOfPtnrSearchResult(Long count, Long total, List<BpCtgryOfPtnr> resultList,
			CoreSearchInput<BpCtgryOfPtnr> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
