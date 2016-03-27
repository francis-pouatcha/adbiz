package org.adorsys.adcore.loader.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrJobCstr;
import org.adorsys.adcore.loader.jpa.CorLdrStep;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.adorsys.adcore.xls.StepCallback;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CorLdrConstraintChecker extends CoreAbstLoader<CorLdrJobCstr>{
	
	private static Logger LOG = Logger.getLogger(CorLdrConstraintChecker.class.getName());
	
	@Inject
	private CorLdrStepLookup stepLookup;
	
	@EJB
	private CorLdrConstraintChecker constraintChecker;

	@Override
	protected CorLdrJobCstr newObject() {
		return new CorLdrJobCstr();
	}

	@Override
	protected CoreAbstLoader<CorLdrJobCstr> getLoader() {
		return constraintChecker;
	}

	@Override
	protected StepCallback getStepCallback() {
		return null;
	}

	/**
	 * Check if constraint applies, if not return null;
	 */
	@Override
	public CorLdrJobCstr save(CorLdrJobCstr cstr, List<PropertyDesc> fields) {
		if(StringUtils.isBlank(cstr.getCntnrIdentif()) || StringUtils.isBlank(cstr.getEntIdentif())) return cstr;
		LOG.info(cstr.getCstrDetail() +  " : Checking constraints : " + cstr.getEntIdentif());
		Long count = stepLookup.countByEntIdentif(cstr.getEntIdentif());
		if(count<=0) {
			LOG.info(cstr.getCstrDetail() +  " : No constraint found for : " + cstr.getEntIdentif());
			return null;
		}
		int processed = 0;
		int max = 100;
		while(processed<count){
			int start = processed;
			processed+=max;
			List<CorLdrStep> steps = stepLookup.findByEntIdentif(cstr.getEntIdentif(), start, max);
			for (CorLdrStep corLdrStep : steps) {
				LOG.info(cstr.getCstrDetail() +  " : Checking constraints : " + corLdrStep.getEntIdentif());
				if(corLdrStep.getEnded()==null) {
					LOG.warning(cstr.getCstrDetail() +  " : Contraining step " + corLdrStep.getEntIdentif() + " not yet ended. Job will wait.");
					return null;
				}
			}
		}
		return cstr;
	}

}
