package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class PrcmtDlvryItemSearchResult extends CoreAbstBsnsItemSearchResult<PrcmtDlvryItem>{

	public PrcmtDlvryItemSearchResult() {
	}

	public PrcmtDlvryItemSearchResult(Long count,
			List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
