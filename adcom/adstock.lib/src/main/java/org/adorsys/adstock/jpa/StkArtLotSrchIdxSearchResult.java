package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class StkArtLotSrchIdxSearchResult extends CoreAbstIdentifObjectSearchResult<StkArtLotSrchIdx>
{

	public StkArtLotSrchIdxSearchResult() {
		super();
	}

	public StkArtLotSrchIdxSearchResult(Long count,
			List<StkArtLotSrchIdx> resultList,
			CoreAbstIdentifObjectSearchInput<StkArtLotSrchIdx> searchInput) {
		super(count, resultList, searchInput);
	}
}
