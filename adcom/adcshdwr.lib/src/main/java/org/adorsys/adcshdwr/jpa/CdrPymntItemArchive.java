package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.annotation.Description;

@Entity
@Description("CdrPymntItem_description")
public class CdrPymntItemArchive extends CdrAbstPymntItem {
	private static final long serialVersionUID = -6949990480307553066L;
}