package org.adorsys.adcore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FluentArray {
	
	private List<String> list; 
	
	private FluentArray(List<String> list) {
		this.list = new ArrayList<String>(list);
	}
	public static FluentArray asList(String... es){
		return new FluentArray(Arrays.asList(es));
	}
	public FluentArray addAll(Collection<String> c){
		for (String string : c) {
			list.add(string);
		}
		return this;
	}
	public Collection<String> toCol(){
		return Collections.unmodifiableCollection(list);
	}
}
