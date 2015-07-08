package org.adorsys.adbase.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name="BaseRoleEntry")
@Description("RoleEntry_description")
public class RoleEntry extends CoreAbstTimedData {

	private static final long serialVersionUID = -7224432277431606183L;

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(getIdentif()))
			throw new IllegalStateException("Identifier must be set explicitely.");
		return getIdentif();
	}
}