package org.adorsys.adinvtry.api;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchResult;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtry_;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryJob;
import org.adorsys.adinvtry.jpa.InvInvtryStep;
import org.adorsys.adinvtry.rest.InvInvtryInjector;
import org.adorsys.adinvtry.rest.InvInvtrySearchInput;
import org.adorsys.adinvtry.rest.InvInvtrySearchResult;

@Stateless
public class InvInvtryManager  extends CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{

	@Inject
	private InvInvtryInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvInvtryJob, InvInvtryStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected Field[] getEntityFields() {
		return InvInvtry_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsObjectSearchResult<InvInvtry, CoreAbstBsnsObjectSearchInput<InvInvtry>> newSearchResult(
			Long size, List<InvInvtry> resultList,
			CoreAbstBsnsObjectSearchInput<InvInvtry> searchInput) {
		return new InvInvtrySearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsObjectSearchInput<InvInvtry> newSearchInput() {
		return new InvInvtrySearchInput();
	}
	






//	
//	private void checkCandidateContainer(InvInvtry containerInvtry){
//		if(containerInvtry==null)
//			throw new IllegalArgumentException("InvInvtry_candidateContainerNotFound_error");
//		// COnditions
//		if(containerInvtry.getContainerId()!=null || containerInvtry.getMergedDate()!=null)
//			throw new IllegalArgumentException("InvInvtry_mergedCanNotContain_error");
//		if(containerInvtry.getPostedDate()!=null || containerInvtry.getMergedDate()!=null)
//			throw new IllegalArgumentException("InvInvtry_postedCanNotContain_error");
//	}
//
//	private void checkCandidateMerge(InvInvtry invtry, InvInvtry containerInvtry){
//		if(invtry==null)
//			throw new IllegalArgumentException("InvInvtry_candidateMergeNotFound_error");
//		// Conditions
//		if(invtry.getPostedDate()!=null)
//			throw new IllegalArgumentException("InvInvtry_postedCanNotBeMerged_error");
//		if(invtry.getContainerId()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
//			throw new IllegalArgumentException("InvInvtry_mergeCandAssignedToAnotherContainer_error");
//		if(invtry.getMergedDate()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
//			throw new IllegalArgumentException("InvInvtry_mergeCandMergedIntoAnotherContainer_error");
//	}
}
