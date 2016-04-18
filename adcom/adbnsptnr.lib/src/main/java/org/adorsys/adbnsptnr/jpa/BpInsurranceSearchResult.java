package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpInsurranceSearchResult extends CoreAbstIdentifObjectSearchResult<BpInsurrance> {

	public BpInsurranceSearchResult() {
		super();
	}

	public BpInsurranceSearchResult(Long count, List<BpInsurrance> resultList,
			CoreAbstIdentifObjectSearchInput<BpInsurrance> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpInsurranceSearchResult(Long count, Long total, List<BpInsurrance> resultList,
			CoreSearchInput<BpInsurrance> searchInput) {
		super(count, total, resultList, searchInput);
	}
}