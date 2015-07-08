package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalPicMappingSearchResult extends CoreAbstIdentifObjectSearchResult<CatalPicMapping, CatalPicMappingSearchInput>{

	public CatalPicMappingSearchResult() {
		super();
	}

	public CatalPicMappingSearchResult(Long count,
			List<CatalPicMapping> resultList,
			CatalPicMappingSearchInput searchInput) {
		super(count, resultList, searchInput);
	}

}
