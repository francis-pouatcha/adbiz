package org.adorsys.adbase.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name="BasePricingCurrRate")
public class PricingCurrRate extends BaseAbstractCurrRate {

	private static final long serialVersionUID = -7649041196331977969L;
}