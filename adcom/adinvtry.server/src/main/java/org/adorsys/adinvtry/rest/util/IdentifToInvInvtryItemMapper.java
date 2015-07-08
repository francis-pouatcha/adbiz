/**
 * 
 */
package org.adorsys.adinvtry.rest.util;

import org.adorsys.adinvtry.jpa.InvInvtryItem;

/**
 * @author boriswaguia
 *
 */
public class IdentifToInvInvtryItemMapper extends AbstractMapper<InvInvtryItem,String, InvInvtryItem> {
	@Override
	public String getKey() {
		return entity.getIdentif();
	}

	/* (non-Javadoc)
	 * @see org.adorsys.adinvtry.rest.util.ListToMapMapper#getValue()
	 */
	@Override
	public InvInvtryItem getValue() {
		return entity;
	}

	/* (non-Javadoc)
	 * @see org.adorsys.adinvtry.rest.util.AbstractMapper#setEntity(java.lang.Object)
	 */
	@Override
	public void setEntity(InvInvtryItem t) {
		this.entity = t;
	}
	

}
