package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkArticleLot2StrgSctnSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<StkArticleLot2StrgSctn, CoreAbstIdentifObjectSearchInput<StkArticleLot2StrgSctn>> {

	public StkArticleLot2StrgSctnSearchResult() {
		super();
	}

	public StkArticleLot2StrgSctnSearchResult(Long count,
			List<StkArticleLot2StrgSctn> resultList,
			CoreAbstIdentifObjectSearchInput<StkArticleLot2StrgSctn> searchInput) {
		super(count, resultList, searchInput);
	}
}
