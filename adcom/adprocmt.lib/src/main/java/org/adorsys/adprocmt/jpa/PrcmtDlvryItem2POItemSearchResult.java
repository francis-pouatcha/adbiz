package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvryItem2POItemSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2POItem>
{

	public PrcmtDlvryItem2POItemSearchResult() {
	}

	public PrcmtDlvryItem2POItemSearchResult(Long count, List<PrcmtDlvryItem2POItem> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2POItem> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvryItem2POItemSearchResult(Long count, Long total, List<PrcmtDlvryItem2POItem> resultList,
			CoreSearchInput<PrcmtDlvryItem2POItem> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
