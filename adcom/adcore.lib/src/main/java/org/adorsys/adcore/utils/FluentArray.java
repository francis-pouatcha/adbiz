package org.adorsys.adcore.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FluentArray {
	
	private List<String> list; 
	
	private FluentArray(List<String> list) {
		this.list = list;
	}
	public static FluentArray asList(String... es){
		return new FluentArray(Arrays.asList(es));
	}
	public FluentArray addAll(Collection<String> c){
		list.addAll(c);
		return this;
	}
	public Collection<String> toCol(){
		return Collections.unmodifiableCollection(list);
	}
}
