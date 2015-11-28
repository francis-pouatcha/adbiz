package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class PrcmtDlvryArtPrcssngSearchResult extends CoreAbstIdentifObjectSearchResult<PrcmtDlvryArtPrcssng>
{

	public PrcmtDlvryArtPrcssngSearchResult() {
	}

	public PrcmtDlvryArtPrcssngSearchResult(Long count, List<PrcmtDlvryArtPrcssng> resultList,
			CoreAbstIdentifObjectSearchInput<PrcmtDlvryArtPrcssng> searchInput) {
		super(count, resultList, searchInput);
	}

	public PrcmtDlvryArtPrcssngSearchResult(Long count, Long total, List<PrcmtDlvryArtPrcssng> resultList,
			CoreSearchInput<PrcmtDlvryArtPrcssng> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
