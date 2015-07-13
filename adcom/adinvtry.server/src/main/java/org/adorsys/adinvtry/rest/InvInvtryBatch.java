package org.adorsys.adinvtry.rest;

import java.util.UUID;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstBsnsObjBatch;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.jpa.InvInvtryStep;

@Singleton
public class InvInvtryBatch extends CoreAbstBsnsObjBatch<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> {
	
	private final String processId = UUID.randomUUID().toString();

	@Inject
	private InvInvtryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected String getCurrentProcessIdentif() {
		return processId;
	}

}
