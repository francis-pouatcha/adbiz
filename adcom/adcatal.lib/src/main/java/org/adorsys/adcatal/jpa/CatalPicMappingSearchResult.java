package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalPicMappingSearchResult extends CoreAbstIdentifObjectSearchResult<CatalPicMapping>{

	public CatalPicMappingSearchResult() {
		super();
	}

	public CatalPicMappingSearchResult(Long count,
			List<CatalPicMapping> resultList,
			CoreAbstIdentifObjectSearchInput<CatalPicMapping> searchInput) {
		super(count, resultList, searchInput);
	}

}
