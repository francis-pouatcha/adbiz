package org.adorsys.adstock.jpa;

import java.util.UUID;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

@Entity
@Description("StkLotMgmnt_description")
public class StkArtLotMgmnt extends StkAbstArtLotMgmnt {

	private static final long serialVersionUID = -6604822461764901774L;

	@Override
	protected String makeIdentif() {
		return UUID.randomUUID().toString();
	}

}