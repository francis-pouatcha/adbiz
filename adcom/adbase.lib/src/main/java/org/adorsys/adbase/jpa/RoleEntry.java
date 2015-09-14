package org.adorsys.adbase.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity
@Table(name = "BaseRoleEntry")
@Description("RoleEntry_description")
public class RoleEntry extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -7224432277431606183L;

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identifier must be set explicitely.");
	}
}