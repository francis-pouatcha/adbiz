package org.adorsys.adcore.jpa;

import javax.persistence.MappedSuperclass;

/**
 * The itemNbr is the identif
 * The business object number is the cntnrIdentif
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstBsnsItemHeader extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 6817986076266080624L;

	private static final CoreAbstBsnsItemHeader singleton = new CoreAbstBsnsItemHeader() {
		private static final long serialVersionUID = 6627591736322872671L;
		@Override
		protected String makeIdentif() {
			return null;
		}
	};
	protected void resetHeader(){
		singleton.copyTo(this);
	}
	
}