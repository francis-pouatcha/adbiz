package org.adorsys.adprocmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

/**
 * Holds the processing state of an article being delivered.
 * 
 * @author francis
 *
 */
@Entity
@Description("PrcmtDlvryArtPrcssng_description")
public class PrcmtDlvryArtPrcssng extends CoreAbstIdentifObject {
	
	private static final long serialVersionUID = 3429165427168415386L;

	@Column
	@Description("PrcmtDlvryArtPrcssng_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	@Column
	@Description("PrcmtDlvryArtPrcssng_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("PrcmtDlvryArtPrcssng_prcssngAgent_description")
	private String prcssngAgent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDlvryArtPrcssng_prcssngEndTme_description")
	private Date prcssngEndTme;
	
	public String getDlvryNbr() {
		return dlvryNbr;
	}

	public void setDlvryNbr(String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public String getPrcssngAgent() {
		return prcssngAgent;
	}

	public void setPrcssngAgent(String prcssngAgent) {
		this.prcssngAgent = prcssngAgent;
	}

	public Date getPrcssngEndTme() {
		return prcssngEndTme;
	}

	public void setPrcssngEndTme(Date prcssngEndTme) {
		this.prcssngEndTme = prcssngEndTme;
	}

	@Override
	protected String makeIdentif() {
		return toId(dlvryNbr, artPic);
	}
	
	public static String toId(String dlvryNbr, String artPic){
		return dlvryNbr + "_" + artPic;
	}
}