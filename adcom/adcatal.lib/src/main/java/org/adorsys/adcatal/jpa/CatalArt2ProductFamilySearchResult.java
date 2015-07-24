package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArt2ProductFamilySearchResult extends CoreAbstIdentifObjectSearchResult<CatalArt2ProductFamily> {

	public CatalArt2ProductFamilySearchResult() {
		super();
	}

	public CatalArt2ProductFamilySearchResult(Long count, Long total,
			List<CatalArt2ProductFamily> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArt2ProductFamily> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
