package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BaseCountryNameSearchResult extends CoreAbstIdentifObjectSearchResult<BaseCountryName>
{
	public BaseCountryNameSearchResult() {
		super();
	}

	public BaseCountryNameSearchResult(Long count,
			List<BaseCountryName> resultList,
			CoreAbstIdentifObjectSearchInput<BaseCountryName> searchInput) {
		super(count, resultList, searchInput);
	}

	public BaseCountryNameSearchResult(Long count, Long total,
			List<BaseCountryName> resultList,
			CoreSearchInput<BaseCountryName> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
