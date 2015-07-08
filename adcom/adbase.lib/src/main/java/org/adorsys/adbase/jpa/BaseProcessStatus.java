package org.adorsys.adbase.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreDynEnum;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BaseProcessStatus_description")
public class BaseProcessStatus extends CoreDynEnum {
	private static final long serialVersionUID = 652055187511281118L;
}