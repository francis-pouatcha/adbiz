package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class StkArt2SectionSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<StkArt2Section> {

	public StkArt2SectionSearchResult() {
		super();
	}

	public StkArt2SectionSearchResult(Long count,
			List<StkArt2Section> resultList,
			CoreAbstIdentifObjectSearchInput<StkArt2Section> searchInput) {
		super(count, resultList, searchInput);
	}

	public StkArt2SectionSearchResult(Long count, Long total,
			List<StkArt2Section> resultList,
			CoreSearchInput<StkArt2Section> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
