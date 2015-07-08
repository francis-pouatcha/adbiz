package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtLangMappingSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtLangMapping, CatalArtLangMappingSearchInput>
{

	public CatalArtLangMappingSearchResult() {
		super();
	}

	public CatalArtLangMappingSearchResult(Long count,
			List<CatalArtLangMapping> resultList,
			CatalArtLangMappingSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
	
}
