package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;

@XmlRootElement
public class PrcmtProcOrderSearchResult extends CoreAbstBsnsObjectSearchResult<PrcmtProcOrder>{

	public PrcmtProcOrderSearchResult() {
		super();
	}

	public PrcmtProcOrderSearchResult(Long count,
			List<PrcmtProcOrder> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtProcOrder> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
