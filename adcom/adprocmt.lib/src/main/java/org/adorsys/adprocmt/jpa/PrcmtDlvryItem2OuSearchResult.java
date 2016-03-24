package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvryItem2OuSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2Ou>
{

	public PrcmtDlvryItem2OuSearchResult() {
	}

	public PrcmtDlvryItem2OuSearchResult(Long count, List<PrcmtDlvryItem2Ou> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2Ou> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvryItem2OuSearchResult(Long count, Long total, List<PrcmtDlvryItem2Ou> resultList,
			CoreSearchInput<PrcmtDlvryItem2Ou> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
