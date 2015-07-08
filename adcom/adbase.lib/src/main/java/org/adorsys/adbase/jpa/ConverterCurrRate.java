package org.adorsys.adbase.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="BaseConvCurrRate")
public class ConverterCurrRate extends BaseAbstractCurrRate {

	private static final long serialVersionUID = -5833009373093224963L;
}