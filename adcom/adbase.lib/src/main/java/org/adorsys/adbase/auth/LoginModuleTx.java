package org.adorsys.adbase.auth;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.rest.ConnectionHistoryEJB;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adbase.rest.SecTermCredtlEJB;
import org.adorsys.adbase.rest.SecTermSessionEJB;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.adorsys.adbase.rest.SecUserSessionEJB;
import org.adorsys.adcore.auth.AuthParams;
import org.adorsys.adcore.auth.OpId;
import org.adorsys.adcore.auth.TermCdtl;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.utils.AccessTimeValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Used to cover tx boundaries in the login module.
 * 
 * @author francis
 *
 */
@Stateless
public class LoginModuleTx {
	
	@Inject
	private ConnectionHistoryEJB connectionHistoryEJB;
	
	@Inject
	private SecUserSessionEJB secUserSessionEJB;
	
	@Inject
	private SecTermSessionEJB secTermSessionEJB;

	@Inject
	private SecTermCredtlEJB secTermCredtlEJB;
	
	@Inject
	private LoginEJB loginEJB;
	
	@Inject
	private SecTerminalEJB secTerminalEJB;	

	public TermWsUserPrincipal login_login(SecUserSession existingUserSession, 
			SecTermSession secTermSession, SecTerminal secTerminal, 
			TermCdtl termCdtl, Date currentDate, String ouId, String workspaceId, String langIso2){
		
		if(existingUserSession!=null && StringUtils.isNotBlank(existingUserSession.getId())){
			secUserSessionEJB.deleteById(existingUserSession.getId());
		}

		SecUserSession secUserSession = new SecUserSession();
		secUserSession.setCreated(currentDate);
		secUserSession.setExpires(DateUtils.addDays(currentDate, 1));
		secUserSession.setId(UUID.randomUUID().toString());
		secUserSession.setLoginName(secTermSession.getLoginName());
		secUserSession.setLangIso2(langIso2);
		secUserSession.setTermSessionId(secTermSession.getId());
		secUserSession.setOuId(ouId);
		// Setin upthe workspace identifier 
		secUserSession.setWorkspaceId(workspaceId);

		secUserSession = secUserSessionEJB.create(secUserSession);

		// Update terminal data
		secTermSession.setLoginName(secTermSession.getLoginName());
		secTermSession.setUserSession(secUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, secUserSession.getLoginName(), secUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), secUserSession.getWorkspaceId(),OpId.login.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(secTermSession.getLoginName());
		newPrincipal.setLangIso2(secUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(secUserSession.getId());
		newPrincipal.setWorkspaceId(secUserSession.getWorkspaceId());
		return newPrincipal;
	}
	
	public TermWsUserPrincipal login_wsin(SecUserSession existingUserSession, SecTermSession secTermSession, 
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate){
		SecUserSession secUserSession = new SecUserSession();
		secUserSession.setCreated(currentDate);
		secUserSession.setExpires(DateUtils.addDays(currentDate, 1));
		secUserSession.setId(UUID.randomUUID().toString());
		secUserSession.setLoginName(existingUserSession.getLoginName());
		secUserSession.setLangIso2(existingUserSession.getLangIso2());
		secUserSession.setTermSessionId(secTermSession.getId());
		secUserSession.setWorkspaceId(existingUserSession.getWorkspaceId());
		secUserSession.setOuId(existingUserSession.getOuId());
		secUserSessionEJB.deleteById(existingUserSession.getId());
		secUserSession = secUserSessionEJB.create(secUserSession);

		// Update terminal data
		secTermSession.setLoginName(secUserSession.getLoginName());
		secTermSession.setUserSession(secUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, secUserSession.getLoginName(), secUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), secUserSession.getWorkspaceId(),OpId.wsin.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(secUserSession.getLoginName());
		newPrincipal.setLangIso2(secUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(secUserSession.getId());
		newPrincipal.setWorkspaceId(secUserSession.getWorkspaceId());
		return newPrincipal;
		
	}

	public TermWsUserPrincipal login_wsout(SecUserSession existingUserSession, SecTermSession secTermSession, 
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate, String newWorkspace){
		
		String newUserSessionIdString = UUID.randomUUID().toString();
		SecUserSession newUserSession = new SecUserSession();
		newUserSession.setId(newUserSessionIdString);
		newUserSession.setCreated(new Date());
		newUserSession.setExpires(DateUtils.addDays(new Date(), 1));
		newUserSession.setTermSessionId(secTermSession.getId());
		newUserSession.setLoginName(existingUserSession.getLoginName());
		newUserSession.setLangIso2(existingUserSession.getLangIso2());
		newUserSession.setOuId(existingUserSession.getOuId());
		newUserSession.setWorkspaceId(newWorkspace);
		secUserSessionEJB.deleteById(existingUserSession.getId());
		newUserSession = secUserSessionEJB.create(newUserSession);
		
		// Update terminal data
		secTermSession.setLoginName(newUserSession.getLoginName());
		secTermSession.setUserSession(newUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, newUserSession.getLoginName(), newUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), newUserSession.getWorkspaceId(),OpId.wsout.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(newUserSession.getLoginName());
		newPrincipal.setLangIso2(newUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(newUserSession.getId());
		newPrincipal.setWorkspaceId(newUserSession.getWorkspaceId());
		return newPrincipal;
	}
	
	public TermWsUserPrincipal login_logout(TermWsUserPrincipal existingPrincipal, 
			SecUserSession existingUserSession, SecTermSession secTermSession,
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate){
		
		if(existingUserSession!=null && StringUtils.isNotBlank(existingUserSession.getId())){
			secUserSessionEJB.deleteById(existingUserSession.getId());

			ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
					currentDate, existingUserSession.getLoginName(), existingUserSession.getOuId(),
					termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), existingUserSession.getWorkspaceId(),OpId.logout.name());
			connectionHistoryEJB.create(connectionHistory);
		}
		
		// Update terminal data
		secTermSession.setLoginName(null);
		secTermSession.setUserSession(null);
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		if(existingPrincipal!=null){
			existingPrincipal.setLoginName(null);
			existingPrincipal.setLoginTime(currentDate);
			existingPrincipal.setUserSessionId(null);
			existingPrincipal.setTermSessionId(secTermSession.getId());
			existingPrincipal.setWorkspaceId(existingUserSession.getWorkspaceId());
			return existingPrincipal;
		} else {
			// instantiate principal
			TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
			newPrincipal.setAccessTime(secTerminal.getAccessTime());
			newPrincipal.setLocality(secTerminal.getLocality());
			newPrincipal.setLoginName(null);
			newPrincipal.setLoginTime(currentDate);
			newPrincipal.setMacAddress(secTerminal.getMacAddress());
			newPrincipal.setTermCred(termCdtl.getCre());
			newPrincipal.setTermName(secTerminal.getTermName());
			newPrincipal.setTermSessionId(secTermSession.getId());
			newPrincipal.setTimeZone(secTerminal.getTimeZone());
			newPrincipal.setUserSessionId(null);
			newPrincipal.setWorkspaceId(existingUserSession.getWorkspaceId());
			
			return newPrincipal;
		}
		
	}

	public void logWrongPassword(Login login, String ipAdress,
			String termCdtl, String terminalName, String terminalId, String workspaceId,
			Date currentDate) {
		ConnectionHistory history = connectionHistoryEJB.findByLoginName(login.getLoginName());
		if (history != null) {
			if (BigInteger.valueOf(3).compareTo(history.getCounter()) <= 0) {
				login.setAccountLocked(Boolean.TRUE);
				login = loginEJB.update(login);
				newConnectionHistory(history.getCounter().add(BigInteger.ONE), history.getFirstLoginDate(), 
						ipAdress, currentDate, login.getLoginName(), login.getOuIdentif(), termCdtl, 
						terminalName, terminalId, workspaceId, OpId.login.name());
			}
		} else {
			newConnectionHistory(BigInteger.ONE, currentDate, 
					ipAdress, currentDate, login.getLoginName(), login.getOuIdentif(), termCdtl, 
					terminalName, terminalId, workspaceId, OpId.login.name());
		}
	}
	
	private ConnectionHistory newConnectionHistory(BigInteger counter,
			Date firstLoginDate, String ipAdress, Date lastLoginDate,
			String loginName, String ouId, String termCdtl,
			String terminalName, String terminalId, String workspaceId, String opr) {
		ConnectionHistory h = new ConnectionHistory();
		h.setCounter(counter);
		h.setFirstLoginDate(firstLoginDate);
		h.setIpAdress(ipAdress);
		h.setLastLoginDate(lastLoginDate);
		h.setLoginName(loginName);
		h.setOuId(ouId);
		h.setTermCdtl(termCdtl);
		h.setTerminalName(terminalName);
		h.setWorkspaceId(workspaceId);
		h.setTerminalId(terminalId);
		h.setOpr(opr);
		return h;
	}
	
	public SecTermSession readTerminalSession(AuthParams auth, TermCdtl termCdtl, HttpServletRequest request){
		String opr = auth.getOpr();
		String providedTermCred = termCdtl.getCre();
		SecTermCredtl secTermCredtl = secTermCredtlEJB.findById(providedTermCred);
		if (secTermCredtl == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_CRED_NOT_FOUND_ERROR.name());
			return null;
		}
		setAttr(request, "USER-LANG", secTermCredtl.getLangIso2());

		if (StringUtils.isBlank(auth.getTrm())){
			// This situation can only happen in the case of a login or workspace in.
			if(!OpId.login.name().equals(opr)){
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_TERM_SESSION_ERROR.name());
				return null;
			}
		}
		
		SecTermSession secTermSession = null;

		String storedTermSessionId = secTermCredtl.getTermSessionId();
		// Means there is no session associated with this terminal
		if(StringUtils.isNotBlank(auth.getTrm())){
			if(!StringUtils.equals(storedTermSessionId,auth.getTrm())){
				// This can not be.
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.WRONG_TERM_SESSION_ERROR.name());
				return null;
			}
			secTermSession = secTermSessionEJB.findById(auth
					.getTrm());
			if (secTermSession != null) return secTermSession;
			
			returnWithAttr(request, "AUTH-ERROR",
				MessagesKeys.NO_TERM_SESSION_ERROR.name());
			return null;
		}

		if(StringUtils.isBlank(storedTermSessionId)){
			if(StringUtils.isBlank(termCdtl.getCert())){
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_TERM_SESSION_ERROR.name());
				return null;
			} 
			// create the term session
			Date now = new Date();
			secTermSession = new SecTermSession();
			secTermSession.setTermName(secTermCredtl.getTermName());
			secTermSession.setCreated(now);
			secTermSession.setExpires(DateUtils.addYears(now, 1));
			secTermSession.setTermId(secTermCredtl.getTermId());
			secTermSession.setTermCredtl(secTermCredtl.getId());
			secTermSession.setLangIso2(secTermCredtl.getLangIso2());
			secTermSession = secTermSessionEJB.create(secTermSession);
			storedTermSessionId = secTermSession.getId();
			secTermCredtl.setTermSessionId(storedTermSessionId);
			secTermCredtlEJB.update(secTermCredtl);
			return secTermSession;
		}

		// Identify the terminal
		secTermSession = secTermSessionEJB.findById(storedTermSessionId);
		if (secTermSession == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_CRED_NOT_FOUND_ERROR.name());
			return null;
		}
		// validate the session if not a ssl session
		if(StringUtils.isBlank(termCdtl.getCert()) && !StringUtils.equals(auth.getTky(), secTermSession.getTermKey())){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_AUTH_ERROR.name());
			return null;
		}
		return secTermSession;
	}
	
	public SecTerminal readAndCheckSecTerminal(SecTermSession secTermSession, HttpServletRequest request, Date currentDate){
		SecTerminal secTerminal = secTerminalEJB.findById(secTermSession.getTermId());
		if (secTerminal == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.UNKNOWN_TERM_ERROR.name());
			return null;
		}

		// Validate terminal
		if(!checkLocality(request, secTerminal.getLocality())) return null;
		if(!checkValidity(request, secTerminal.getValidFrom(), secTerminal.getValidTo(), currentDate)) return null;
		if(!checkTermAccessTime(request, secTerminal.getAccessTime(), secTerminal.getTimeZone())) return null;
		return secTerminal;
	}
	
	

	private boolean returnWithAttr(HttpServletRequest request, String key,
			String value) {
		if (request != null)
			request.setAttribute(key, value);
		return false;
	}

	private void setAttr(HttpServletRequest request, String key,
			String value) {
		if (request != null)
			request.setAttribute(key, value);
	}

	
	private boolean checkTermAccessTime(HttpServletRequest request, String accessTime, String timeZone){
		if(StringUtils.isBlank(accessTime) || StringUtils.isBlank(timeZone)) return true;
		if(!AccessTimeValidator.check(accessTime, timeZone))
			return returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_ACCESS_TIME_ERROR.name());
		return true;
	}
	
	private boolean checkLocality(HttpServletRequest request, String locality){
		if(StringUtils.isBlank(locality)) return true;
		
		// check locality
		
		return true;
	}

	private boolean checkValidity(HttpServletRequest request, Date validFrom, Date validTo, Date currentDate){
		if((validFrom!=null && currentDate.before(validFrom)) || (validTo!=null && currentDate.after(validTo)))
			return returnWithAttr(request, "AUTH-ERROR",MessagesKeys.TERM_VALIDITY_ERROR.name());
		return true;
	}
}
