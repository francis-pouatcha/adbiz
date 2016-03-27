package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvryItem2StrgSctnSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvryItem2StrgSctn>
{

	public PrcmtDlvryItem2StrgSctnSearchResult() {
	}

	public PrcmtDlvryItem2StrgSctnSearchResult(Long count, List<PrcmtDlvryItem2StrgSctn> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvryItem2StrgSctn> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvryItem2StrgSctnSearchResult(Long count, Long total, List<PrcmtDlvryItem2StrgSctn> resultList,
			CoreSearchInput<PrcmtDlvryItem2StrgSctn> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
