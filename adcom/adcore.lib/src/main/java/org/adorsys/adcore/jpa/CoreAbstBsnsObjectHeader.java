package org.adorsys.adcore.jpa;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.utils.FluentArray;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * These are descriptive information on a business object. Without the payload.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class CoreAbstBsnsObjectHeader extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -945810984704059810L;

	@Column
	private String orgIdentif;

	@Column
	private String ouIdentif;

	protected static final Collection<String> coreAbstBsnsObjectHeaderExcludeFields =FluentArray.asList("identif").addAll(coreAbstIdentifObjectExcludeFields).toCol(); 
	public void contentEquals(CoreAbstEntity rhs){
		EqualsBuilder.reflectionEquals(this, rhs, coreAbstBsnsObjectHeaderExcludeFields);
	}
	
	private static CoreAbstBsnsObjectHeader singleton = new CoreAbstBsnsObjectHeader() {
		private static final long serialVersionUID = -8858062430556170788L;
		@Override
		protected String makeIdentif() {return null;}
	};
	protected void resetHeader(){
		singleton.copyTo(this);
	}
	
	public String getOrgIdentif() {
		return orgIdentif;
	}
	public void setOrgIdentif(String orgIdentif) {
		this.orgIdentif = orgIdentif;
	}
	public String getOuIdentif() {
		return ouIdentif;
	}
	public void setOuIdentif(String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}
}