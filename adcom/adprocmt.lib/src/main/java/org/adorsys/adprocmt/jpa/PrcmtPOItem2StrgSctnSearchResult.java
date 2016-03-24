package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtPOItem2StrgSctnSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtPOItem2StrgSctn>
{

	public PrcmtPOItem2StrgSctnSearchResult() {
	}

	public PrcmtPOItem2StrgSctnSearchResult(Long count, List<PrcmtPOItem2StrgSctn> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtPOItem2StrgSctn> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtPOItem2StrgSctnSearchResult(Long count, Long total, List<PrcmtPOItem2StrgSctn> resultList,
			CoreSearchInput<PrcmtPOItem2StrgSctn> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
