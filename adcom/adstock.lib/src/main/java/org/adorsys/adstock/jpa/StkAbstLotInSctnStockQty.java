package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;

/**
 * Holds the quantity of an article lot in a specific section.
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class StkAbstLotInSctnStockQty extends StkAbstLotStockQty {

	private static final long serialVersionUID = 4880260992428000390L;

	@Column
	@Description("StkAbstractStockQty_section_description")
	@NotNull
	private String section;


	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String artPicAndLotPicAndSection(){
		return getCntnrIdentif() + "_" + getLotPic() + "_" + getSection();
	}

	@Override
	protected String makeIdentif() {
		return getCntnrIdentif() + "_" + getLotPic() + "_" + getSection() + "_" + getSeqNbr();
	}
}