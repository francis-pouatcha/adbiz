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
public class CdrPymntArchive extends CdrAbstPymnt {
	private static final long serialVersionUID = -6438925388888026745L;
}