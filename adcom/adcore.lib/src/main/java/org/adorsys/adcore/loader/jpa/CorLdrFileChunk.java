package org.adorsys.adcore.loader.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CoreAbstIdentifByteChunk;

@Entity
public class CorLdrFileChunk extends CoreAbstIdentifByteChunk {

	private static final long serialVersionUID = -3654608104290594655L;

	@Override
	protected String makeIdentif() {
		throw new IllegalStateException("Identif expected to be set prior to persist.");
	}
}
