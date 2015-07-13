package org.adorsys.adinvtry.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adinvtry.jpa.InvInvtry;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class InvInvtrySearchInput extends
		CoreAbstBsnsObjectSearchInput<InvInvtry> {
	private List<String> invrtyNbrs = new ArrayList<String>();

	public List<String> getInvrtyNbrs() {
		return invrtyNbrs;
	}

	public void setInvrtyNbrs(List<String> invrtyNbrs) {
		this.invrtyNbrs = invrtyNbrs;
	}
}
