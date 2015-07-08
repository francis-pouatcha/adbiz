package org.adorsys.adstock.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adorsys.adstock.jpa.StkMvnt;

public class StkMvnts {
	
	private Date mvntDte;

	private List<StkMvnt> artLots = new ArrayList<StkMvnt>();

	public List<StkMvnt> getArtLots() {
		return artLots;
	}

	public void setArtLots(List<StkMvnt> artLots) {
		this.artLots = artLots;
	}

	public Date getMvntDte() {
		return mvntDte;
	}

	public void setMvntDte(Date mvntDte) {
		this.mvntDte = mvntDte;
	}
}
