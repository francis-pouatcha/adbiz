package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adinvtry.jpa.InvInvtry;

@XmlRootElement
public class InvInvtrySearchResult extends
		CoreAbstBsnsObjectSearchResult<InvInvtry> {

	public InvInvtrySearchResult() {
		super();
	}

	public InvInvtrySearchResult(Long count, List<InvInvtry> resultList,
			CoreAbstBsnsObjectSearchInput<InvInvtry> searchInput) {
		super(count, resultList, searchInput);
	}
}
