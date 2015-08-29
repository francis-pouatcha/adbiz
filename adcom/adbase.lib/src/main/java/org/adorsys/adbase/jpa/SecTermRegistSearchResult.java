/**
 * 
 */
package org.adorsys.adbase.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

/**
 * @author boriswaguia
 *
 */
public class SecTermRegistSearchResult extends CoreAbstIdentifObjectSearchResult<SecTermRegist>{

	public SecTermRegistSearchResult() {
		super();
	}

	public SecTermRegistSearchResult(Long count,
			List<SecTermRegist> resultList,
			CoreAbstIdentifObjectSearchInput<SecTermRegist> searchInput) {
		super(count, resultList, searchInput);
	}

	public SecTermRegistSearchResult(Long count, Long total,
			List<SecTermRegist> resultList,
			CoreSearchInput<SecTermRegist> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
