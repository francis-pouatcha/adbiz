package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArt2ProductFamilySearchResult extends CoreAbstIdentifObjectSearchResult<CatalArt2ProductFamily, CatalArt2ProductFamilySearchInput> {

	public CatalArt2ProductFamilySearchResult() {
		super();
	}

	public CatalArt2ProductFamilySearchResult(Long count,
			List<CatalArt2ProductFamily> resultList,
			CatalArt2ProductFamilySearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
