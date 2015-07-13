package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@MappedSuperclass
public class StkAbstractLot2Section extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -8174913096678489715L;

	@Column
	@Description("StkAbstractLot2Section_lotPic_description")
	@NotNull
	private String lotPic;

	/*
	 * The section code
	 */
	@Column
	@Description("StkAbstractLot2Section_strgSection_description")
	@NotNull
	private String strgSection;

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

	@Column
	@Description("StkAbstractStockQty_stockQty_description")
	@NotNull
	private BigDecimal stockQty = BigDecimal.ZERO;

	@Column
	@Description("StkAbstractStockQty_rsvrdQty_description")
	@NotNull
	private BigDecimal rsvrdQty = BigDecimal.ZERO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkAbstractStockQty_qtyDt_description")
	@NotNull
	private Date qtyDt;
	
	@Description("StkAbstractStockQty_seqNbr_description")
	private Integer seqNbr;
	
	public String getStrgSection() {
		return strgSection;
	}

	public void setStrgSection(String strgSection) {
		this.strgSection = strgSection;
	}

	public static String toId(String lotPic, String strgSection){
		return lotPic +"_"+ strgSection;
	}
	
	@Override
	protected String makeIdentif() {
		return toId(lotPic, strgSection);
	}

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
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

	public BigDecimal getStockQty() {
		return stockQty;
	}

	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
	}

	public BigDecimal getRsvrdQty() {
		return rsvrdQty;
	}

	public void setRsvrdQty(BigDecimal rsvrdQty) {
		this.rsvrdQty = rsvrdQty;
	}

	public Date getQtyDt() {
		return qtyDt;
	}

	public void setQtyDt(Date qtyDt) {
		this.qtyDt = qtyDt;
	}

	public Integer getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}
}