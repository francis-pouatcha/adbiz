package org.adorsys.adprocmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;

@XmlRootElement
public class PrcmtDeliverySearchResult extends CoreAbstBsnsObjectSearchResult<PrcmtDelivery>
{

	public PrcmtDeliverySearchResult() {
		super();
	}

	public PrcmtDeliverySearchResult(Long count,
			List<PrcmtDelivery> resultList,
			CoreAbstBsnsObjectSearchInput<PrcmtDelivery> searchInput) {
		super(count, resultList, searchInput);
	}
}
