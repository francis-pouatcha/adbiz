package org.adorsys.adstock.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public class StkAbstractLot2Section extends CoreAbstIdentifData {

	private static final long serialVersionUID = -8174913096678489715L;

	@Column
	@Description("StkAbstractLot2Section_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkAbstractLot2Section_artPic_description")
	@NotNull
	private String artPic;

	/*
	 * The name of the article is store here to simplify search.
	 * In the language of the creator of this item.
	 */
	@Column
	@Description("StkAbstractLot2Section_artName_description")
	@NotNull
	private String artName;
	
	/*
	 * The section code
	 */
	@Column
	@Description("StkAbstractLot2Section_strgSection_description")
	@NotNull
	private String strgSection;
	
	/*
	 * The section name
	 */
	@Column
	@Description("StkAbstractLot2Section_artName_description")
	@NotNull
	private String sectionName;
	
	/*
	 * Closed at the inventory process. Waiting for cleanup.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkAbstractLot2Section_closedDt_description")
	private Date closedDt;	

	/*
	 * Date from which this article goes out of stock. It set automatically
	 * by the qty consolidation process.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkAbstractLot2Section_outOfStockDt_description")
	private Date outOfStockDt;	
	
	@Transient
	private StkAbstractSection stkSection;
	
	@Transient
	private StkAbstractArticleLot sectionArticleLot;
	
	public String getStrgSection() {
		return strgSection;
	}

	public void setStrgSection(String strgSection) {
		this.strgSection = strgSection;
	}

	public static String toId(String artPic, String lotPic, String strgSection){
		return artPic + "_" +lotPic +"_"+ strgSection;
	}
	@Override
	protected String makeIdentif() {
		return toId(artPic, lotPic, strgSection);
	}
	
	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public Date getClosedDt() {
		return closedDt;
	}

	public void setClosedDt(Date closedDt) {
		this.closedDt = closedDt;
	}

	public Date getOutOfStockDt() {
		return outOfStockDt;
	}

	public void setOutOfStockDt(Date outOfStockDt) {
		this.outOfStockDt = outOfStockDt;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public void copyTo(StkAbstractLot2Section target){
		target.artPic = artPic;
		target.lotPic = lotPic;
		target.strgSection=strgSection;
		target.sectionName = sectionName;
		target.artName = artName;
		target.stkSection = stkSection;
		target.sectionArticleLot =sectionArticleLot;
	}
	
	public boolean contentEquals(StkAbstractLot2Section target){
		if(!StringUtils.equals(target.strgSection,strgSection)) return false;
		if(!StringUtils.equals(target.lotPic, lotPic)) return false;
		if(!StringUtils.equals(target.artPic, artPic)) return false;
		return true;
	}
	
	public StkAbstractSection getStkSection() {
		return stkSection;
	}

	public void setStkSection(StkAbstractSection stkSection) {
		this.stkSection = stkSection;
	}

	public StkAbstractArticleLot getSectionArticleLot() {
		return sectionArticleLot;
	}

	public void setSectionArticleLot(StkAbstractArticleLot sectionArticleLot) {
		this.sectionArticleLot = sectionArticleLot;
	}
	
}