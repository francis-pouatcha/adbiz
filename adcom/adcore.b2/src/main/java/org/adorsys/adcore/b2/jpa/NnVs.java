package org.adorsys.adcore.b2.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

public class NnVs {
	
	private List<NnV> nnvList = new ArrayList<NnV>();
	private Predicate.BooleanOperator combiner = Predicate.BooleanOperator.AND;

	public NnVs() {
	}
	
	public NnVs(NnV... nnvs) {
		this.nnvList = Arrays.asList(nnvs);
	}
	public NnVs(Predicate.BooleanOperator combiner, NnV... nnvs) {
		this.combiner = combiner;
		this.nnvList = Arrays.asList(nnvs);
	}

	public List<NnV> getNnvList() {
		return nnvList;
	}

	public void setNnvList(List<NnV> nnvList) {
		this.nnvList = nnvList;
	}
	
	public Predicate.BooleanOperator getCombiner() {
		return combiner;
	}

	public void setCombiner(Predicate.BooleanOperator combiner) {
		this.combiner = combiner;
	}

	public static NnVs inst(NnV... nnvs){
		return new NnVs(nnvs);
	}
	public static NnVs inst(Predicate.BooleanOperator combiner, NnV... nnvs){
		return new NnVs(combiner, nnvs);
	}

}
