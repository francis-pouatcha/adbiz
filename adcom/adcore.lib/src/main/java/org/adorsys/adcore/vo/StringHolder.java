package org.adorsys.adcore.vo;

import java.io.Serializable;

public class StringHolder implements Serializable {
	private static final long serialVersionUID = -9020324253221289879L;
	private String content;

	public StringHolder(String content) {
		this.content = content;
	}

	public StringHolder() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
