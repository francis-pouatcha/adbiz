package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkLotStockQtySearchResult extends CoreAbstIdentifObjectSearchResult<StkLotStockQty>
{

	public StkLotStockQtySearchResult() {
		super();
	}

	public StkLotStockQtySearchResult(Long count,
			List<StkLotStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkLotStockQty> searchInput) {
		super(count, resultList, searchInput);
	}
}
