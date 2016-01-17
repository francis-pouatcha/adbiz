package org.adorsys.adcore.b2.jpa;

import javax.persistence.metamodel.SingularAttribute;

public class NnV {

	private final SingularAttribute<? super B2PersContent, ?> attribute;
	private final Object value;
	private final Operation op;
	
	public NnV(SingularAttribute<? super B2PersContent, ?> attribute, Object value) {
		this(attribute, value, Operation.EQUAL);
	}
	public NnV(SingularAttribute<? super B2PersContent, ?> attribute, Object value, Operation op) {
		super();
		this.attribute = attribute;
		this.value = value;
		this.op = op;
	}

	public SingularAttribute<? super B2PersContent, ?> getAttribute() {
		return attribute;
	}

	public Object getValue() {
		return value;
	}
	
	public static NnV inst(SingularAttribute<? super B2PersContent, ?> attribute, Object value){
		return new NnV(attribute, value);
	}
	public static NnV inst(SingularAttribute<? super B2PersContent, ?> attribute, Object value, Operation op){
		return new NnV(attribute, value, op);
	}
	public Operation getOp() {
		return op;
	}
	
}
