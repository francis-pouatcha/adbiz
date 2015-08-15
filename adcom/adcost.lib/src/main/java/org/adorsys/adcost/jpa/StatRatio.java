package org.adorsys.adcost.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.adorsys.adcore.annotation.TmprlCstr;
import org.adorsys.adcore.enums.CoreTmprlDpndncy;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.apache.commons.lang3.StringUtils;

@Entity
public class StatRatio extends CoreAbstIdentifObject {

	/*
	 * The name of this bearer.
	 */
	@Column
	private String desgtn;

	/*
	 * Unité de quantité du ratio
	 */
	@Column
	@TmprlCstr(CoreTmprlDpndncy.ACC_PERIOD)
	private String qtyUnit;

	/*
	 * Création de ratios
	 * 
	 * Bases de référence pour la répartition détaillée/ globale
	 */
	@Enumerated(EnumType.STRING)
	@TmprlCstr(CoreTmprlDpndncy.ACC_PERIOD)
	private StatRatioValueType rationValueType;

	@Override
	protected String makeIdentif() {
		if (StringUtils.isBlank(identif))
			throw new IllegalStateException(
					"Identifier must be set before creation");
		return identif;
	}
}
