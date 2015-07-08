package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class SlsSOItemSearchResult extends CoreAbstBsnsItemSearchResult<SlsSOItem> {
	public SlsSOItemSearchResult() {
		super();
	}

	public SlsSOItemSearchResult(Long count, List<SlsSOItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsSOItem> searchInput) {
		super(count, resultList, searchInput);
	}	
}
