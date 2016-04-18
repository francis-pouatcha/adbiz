package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpIndivPtnrNameSearchResult extends CoreAbstIdentifObjectSearchResult<BpIndivPtnrName> {

	public BpIndivPtnrNameSearchResult() {
		super();
	}

	public BpIndivPtnrNameSearchResult(Long count, List<BpIndivPtnrName> resultList,
			CoreAbstIdentifObjectSearchInput<BpIndivPtnrName> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpIndivPtnrNameSearchResult(Long count, Long total, List<BpIndivPtnrName> resultList,
			CoreSearchInput<BpIndivPtnrName> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
