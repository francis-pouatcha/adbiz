package org.adorsys.adstock.jpa;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
public class StkSectionSearchInput extends CoreAbstIdentifObjectSearchInput<StkSection>{

	private String codeOrName;

	public String getCodeOrName() {
		return codeOrName;
	}

	public void setCodeOrName(String codeOrName) {
		this.codeOrName = codeOrName;
	}

}
