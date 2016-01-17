package org.adorsys.adcore.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

public class NnVLike extends NnV {
	
	public NnVLike(SingularAttribute<? super B2PersContent, String> attribute, String value) {
		super(attribute, prepareString(value), Operation.LIKE);
	}

	private static String prepareString(String value) {
		String result = value;
		if(result==null) return result;
		if(!result.startsWith("%")) result = "%"+result;
		if(!result.endsWith("%")) result = result + "%";
		return result.toLowerCase();
	}

	@SuppressWarnings("unchecked")
	public SingularAttribute<? super B2PersContent, String> getAttribute() {
		return (SingularAttribute<? super B2PersContent, String>) super.getAttribute();
	}

	public String getValue() {
		return (String) super.getValue();
	}
	
	public static NnVLike inst(SingularAttribute<? super B2PersContent, String> attribute, String value){
		return new NnVLike(attribute, value);
	}

}
