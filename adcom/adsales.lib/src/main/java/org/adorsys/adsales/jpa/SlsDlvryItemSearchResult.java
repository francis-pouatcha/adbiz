package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class SlsDlvryItemSearchResult extends CoreAbstBsnsItemSearchResult<SlsDlvryItem>{

	public SlsDlvryItemSearchResult() {
		super();
	}

	public SlsDlvryItemSearchResult(Long count, List<SlsDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<SlsDlvryItem> searchInput) {
		super(count, resultList, searchInput);
	}
}
