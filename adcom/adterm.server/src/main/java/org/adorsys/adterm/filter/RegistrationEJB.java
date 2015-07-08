package org.adorsys.adterm.filter;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.rest.SecTermCredtlEJB;
import org.adorsys.adbase.rest.SecTermRegistEJB;
import org.adorsys.adbase.rest.SecTermSessionEJB;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class RegistrationEJB {

	@Inject
	private SecTermRegistEJB termRegistEJB;
	
	@Inject
	private SecTerminalEJB terminalEJB;
	
	@Inject
	private SecTermSessionEJB termSessionEJB;
	
	@Inject
	private SecTermCredtlEJB termCredtlEJB;
	
	public String register(String registKey, String registPwd, String remoteAddr){

		SecTermRegist regist = termRegistEJB.findByIdentif(registKey, new Date());
		if(regist==null) return null;

		String existingPwd = regist.getRegistrPwd();
		if(!StringUtils.equals(existingPwd, registPwd)){
			return null;
		}

		Date now = new Date();
		Date validFrom = regist.getValidFrom();
		Date validTo = regist.getValidTo();
		if(validFrom.after(now) || (validTo!=null && validTo.before(now))){
			return null;
		}
		
		// then create the terminal
		SecTerminal secTerminal = new SecTerminal(regist);
		secTerminal.setTermId(registKey);
		secTerminal = terminalEJB.create(secTerminal);
		
		// Set the secure cookie.
		SecTermCredtl termCredtl = new SecTermCredtl();
		termCredtl.setTermName(secTerminal.getTermName());
		termCredtl.setCreated(now);
		termCredtl.setExpires(DateUtils.addYears(now, 20));
		termCredtl.setTermId(secTerminal.getId());
		termCredtl = termCredtlEJB.create(termCredtl);
		termCredtl.setLangIso2(secTerminal.getLangIso2());
		String cookieStr = termCredtl.getId();
		
		SecTermSession termSess = new SecTermSession();
		termSess.setTermName(secTerminal.getTermName());
		termSess.setCreated(now);
		termSess.setExpires(DateUtils.addYears(now, 1));
		termSess.setTermId(secTerminal.getId());
		termSess.setTermCredtl(termCredtl.getId());
		termSess.setLangIso2(termCredtl.getLangIso2());
		termSess = termSessionEJB.create(termSess);
		termCredtl.setTermSessionId(termSess.getId());
		termCredtl = termCredtlEJB.update(termCredtl);
		
		termRegistEJB.deleteById(regist.getId());
		
		return cookieStr;
	}
}
