package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class PrcmtPOItemSearchResult  extends CoreAbstBsnsItemSearchResult<PrcmtPOItem>{

	public PrcmtPOItemSearchResult() {
		super();
	}

	public PrcmtPOItemSearchResult(Long count, List<PrcmtPOItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtPOItem> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
