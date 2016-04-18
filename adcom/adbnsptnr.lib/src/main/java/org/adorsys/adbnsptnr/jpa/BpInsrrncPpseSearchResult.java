package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class BpInsrrncPpseSearchResult extends CoreAbstIdentifObjectSearchResult<BpInsrrncPpse> {

	public BpInsrrncPpseSearchResult() {
		super();
	}

	public BpInsrrncPpseSearchResult(Long count, List<BpInsrrncPpse> resultList,
			CoreAbstIdentifObjectSearchInput<BpInsrrncPpse> searchInput) {
		super(count, resultList, searchInput);
	}

	public BpInsrrncPpseSearchResult(Long count, Long total, List<BpInsrrncPpse> resultList,
			CoreSearchInput<BpInsrrncPpse> searchInput) {
		super(count, total, resultList, searchInput);
	}
}