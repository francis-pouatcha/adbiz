package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

@Entity
@Description("CdrPymntItem_description")
public class CdrPymntItem extends CdrAbstPymntItem {
	private static final long serialVersionUID = -7024656097830315990L;
}