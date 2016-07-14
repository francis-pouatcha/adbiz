package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrCshDrawerLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrCshDrawerManager {
	@Inject
	private AdcomUser callerPrincipal;
	
	@Inject
	private CdrCshDrawerEJB ejb;
	@Inject
	private CdrCshDrawerLookup lookup;
	/**
	 * The ZERO field.
	 */
	private static final BigDecimal INITIAL_AMT = BigDecimal.ZERO;
	
	public CdrCshDrawer openCshDrawer(CdrCshDrawer cshDrawer) {
		String loginName = callerPrincipal.getLoginName();
		if(hasOpenedCshDrawer(loginName)) {
			cshDrawer = findOpenedCshDrawerByCashier(loginName).iterator().next();
		}else {
			if(cshDrawer == null) cshDrawer = new CdrCshDrawer();
			String sequence = SequenceGenerator.getSequence(SequenceGenerator.CASHDRAWER_SEQUENCE_PREFIXE);

			cshDrawer.setCashier(loginName);
			cshDrawer.setCdrNbr(sequence);
			cshDrawer.setIdentif(sequence);
			if(cshDrawer.getInitialAmt() == null || cshDrawer.getInitialAmt().intValueExact() < 0) {
				cshDrawer.setInitialAmt(INITIAL_AMT);   
			}
			cshDrawer.setOpngDt(new Date());
			cshDrawer = ejb.update(cshDrawer);
		}
		return cshDrawer;
	}
	
	public CdrCshDrawer closeCshDrawer(CdrCshDrawer cshDrawer) throws AdException {
		String loginName = callerPrincipal.getLoginName();
		if(cshDrawer == null && !hasOpenedCshDrawer(loginName)) throw new AdException("No cash drawer to close.");
		if(cshDrawer == null && hasOpenedCshDrawer(loginName)) {
			cshDrawer = findOpenedCshDrawerByCashier(loginName).iterator().next();
		}
//		computeCshDrawer(cshDrawer);
		cshDrawer.setClsngDt(new Date());
		cshDrawer.setClosedBy(loginName);
		return ejb.update(cshDrawer);
	}

	/**
	 * hasOpenedCshDrawer.
	 *
	 * @return
	 */
	 private boolean hasOpenedCshDrawer(String cashier) {
		if(StringUtils.isBlank(cashier)) {
			cashier = callerPrincipal.getLoginName();
		}
		List<CdrCshDrawer> cshDrawersByCashier = findOpenedCshDrawerByCashier(cashier);
		return !cshDrawersByCashier.isEmpty();
	}

	 
	public List<CdrCshDrawer> findOpenedCshDrawerByCashier(String cashier) {
		return lookup.findOpenedCshDrawerByCashier(cashier);
	}
	
	/**
	 * getActiveCshDrawer.
	 *
	 * @return
	 * @throws AdException 
	 */
	public CdrCshDrawer getActiveCshDrawer() throws AdException {
		String loginName = callerPrincipal.getLoginName();
		List<CdrCshDrawer> cshDrawers = findOpenedCshDrawerByCashier(loginName);
		if(cshDrawers.isEmpty()) return null;
		if(cshDrawers.size() > 1) throw new AdException("More than one active cash drawer for user "+loginName+"\r, the system"
				+ " can't find the default.");
		return cshDrawers.iterator().next();
	}
	
	public List<CdrCshDrawer> findPreviousCdrCshDrawer() {
		String loginName = callerPrincipal.getLoginName();
		List<CdrCshDrawer> previous = lookup.findPrevious(loginName);
		return previous;
	}

}
