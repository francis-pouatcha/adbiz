package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.CoreAbstTimedData;
import org.adorsys.javaext.description.Description;

@Entity 
@Table(name="BaseOuType")
@Description("OuType_description")
public class OuType extends CoreAbstTimedData {

	private static final long serialVersionUID = -9216255546723007865L;

	@Column
	@Description("OuType_idSize_description")
	@NotNull
	private Integer idSize;

	@Column
	@Description("OuType_parentType_description")
	private String parentType;

	@Column
	@Description("OuType_typeName_description")
	@NotNull
	private String typeName;
	
	public Integer getIdSize() {
		return this.idSize;
	}

	public void setIdSize(final Integer idSize) {
		this.idSize = idSize;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(final String parentType) {
		this.parentType = parentType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	protected String makeIdentif() {
		return typeName;
	}
}