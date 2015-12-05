package org.adorsys.adcore.jpa;

import javax.persistence.MappedSuperclass;

/**
 * A byte stream identified 
 * 
 * @author fpo
 *
 */
@MappedSuperclass
public abstract class CoreAbstIdentifByteStream extends CoreAbstIdentifObject {

	private static final long serialVersionUID = -3357904076348888145L;
	
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
