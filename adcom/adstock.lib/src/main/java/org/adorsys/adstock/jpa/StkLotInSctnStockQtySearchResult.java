package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkLotInSctnStockQtySearchResult extends CoreAbstIdentifObjectSearchResult<StkLotInSctnStockQty>
{

	public StkLotInSctnStockQtySearchResult() {
		super();
	}

	public StkLotInSctnStockQtySearchResult(Long count,
			List<StkLotInSctnStockQty> resultList,
			CoreAbstIdentifObjectSearchInput<StkLotInSctnStockQty> searchInput) {
		super(count, resultList, searchInput);
	}
}
