package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstLangObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstLangObjectSearchResult;

@XmlRootElement
public class BpPtnrCtgryDtlsSearchResult extends CoreAbstLangObjectSearchResult<BpPtnrCtgryDtls>{

	public BpPtnrCtgryDtlsSearchResult() {
		super();
	}

	public BpPtnrCtgryDtlsSearchResult(Long count, Long total, List<BpPtnrCtgryDtls> resultList,
			CoreAbstLangObjectSearchInput<BpPtnrCtgryDtls> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
