package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class SlsInvceItemSearchResult extends CoreAbstBsnsItemSearchResult<SlsInvceItem>{

	public SlsInvceItemSearchResult() {
		super();
	}

	public SlsInvceItemSearchResult(Long count, List<SlsInvceItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsInvceItem> searchInput) {
		super(count, resultList, searchInput);
	}
}
