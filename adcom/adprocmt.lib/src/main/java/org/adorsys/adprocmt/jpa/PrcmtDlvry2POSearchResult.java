package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvry2POSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvry2PO>
{

	public PrcmtDlvry2POSearchResult() {
	}

	public PrcmtDlvry2POSearchResult(Long count, List<PrcmtDlvry2PO> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvry2PO> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvry2POSearchResult(Long count, Long total, List<PrcmtDlvry2PO> resultList,
			CoreSearchInput<PrcmtDlvry2PO> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
