package org.adorsys.adstock.jpa;

import javax.persistence.Entity;

import org.adorsys.javaext.description.Description;

/**
 * Document the reservation of a stock quantity.
 * 
 * @author francis
 *
 */
@Entity
@Description("StkRsrvtn_description")
public class StkRsrvtn extends StkAbstractStkRsrvtn {
	private static final long serialVersionUID = 7070672778514185337L;
}