package org.adorsys.adcore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StringListHolder implements Serializable {
	private static final long serialVersionUID = -3267109235629423816L;
	private List<String> list = new ArrayList<String>();

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
