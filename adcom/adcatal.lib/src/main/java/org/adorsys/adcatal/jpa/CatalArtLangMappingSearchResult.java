package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtLangMappingSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtLangMapping>
{

	public CatalArtLangMappingSearchResult() {
		super();
	}

	public CatalArtLangMappingSearchResult(Long count,
			List<CatalArtLangMapping> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtLangMapping> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
