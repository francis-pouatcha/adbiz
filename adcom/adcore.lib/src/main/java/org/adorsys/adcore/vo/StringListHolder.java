package org.adorsys.adcore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StringListHolder implements Serializable {
	private static final long serialVersionUID = -3267109235629423816L;
	private List<String> list = new ArrayList<String>();
	private int start;
	private int max;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
