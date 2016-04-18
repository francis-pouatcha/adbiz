package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

/**
 * identif is cdrNbr
 * 
 * @author fpo
 *
 */
@Entity
@Description("CdrPymnt_description")
public class CdrPymnt extends CdrAbstPymnt {
	private static final long serialVersionUID = -6266862446146119987L;
}