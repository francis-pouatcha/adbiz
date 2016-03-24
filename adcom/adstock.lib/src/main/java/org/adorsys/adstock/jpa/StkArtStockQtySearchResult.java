package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkArtStockQtySearchResult extends CoreAbstIdentifObjectSearchResult<StkArtStockQty>
{

	public StkArtStockQtySearchResult() {
		super();
	}

	public StkArtStockQtySearchResult(Long count,
			List<StkArtStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkArtStockQty> searchInput) {
		super(count, resultList, searchInput);
	}
}
