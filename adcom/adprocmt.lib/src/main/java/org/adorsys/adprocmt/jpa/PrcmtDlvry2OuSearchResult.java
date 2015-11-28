package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvry2OuSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvry2Ou>
{

	public PrcmtDlvry2OuSearchResult() {
	}

	public PrcmtDlvry2OuSearchResult(Long count, List<PrcmtDlvry2Ou> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvry2Ou> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvry2OuSearchResult(Long count, Long total, List<PrcmtDlvry2Ou> resultList,
			CoreSearchInput<PrcmtDlvry2Ou> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
