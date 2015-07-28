package org.adorsys.adbase.auth;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.jboss.security.SecurityConstants;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;

public class SystemLoginModule implements LoginModule {

	/** The subject. */
	private Subject subject;

	/** The callback handler. */
//	private CallbackHandler callbackHandler;

	/**
	 * Flag indicating if the login phase succeeded. Subclasses that override
	 * the login method must set this to true on successful completion of login
	 */
	protected boolean loginOk;

//	private LoginEJB loginEJB;
//	private SecUserSessionEJB secUserSessionEJB;
//	private UserWorkspaceEJB userWorkspaceEJB;
//	private LoginModuleTx loginModuleTx;

	private TermWsUserPrincipal newPrincipal;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
//		this.callbackHandler = callbackHandler;

//		InitialContext initialContext;
//		try {
//			initialContext = new InitialContext();
//			loginEJB = (LoginEJB) initialContext.lookup("java:module/LoginEJB");
//			secUserSessionEJB = (SecUserSessionEJB) initialContext
//					.lookup("java:module/SecUserSessionEJB");
//			userWorkspaceEJB = (UserWorkspaceEJB) initialContext
//					.lookup("java:module/UserWorkspaceEJB");
//			loginModuleTx = (LoginModuleTx) initialContext
//					.lookup("java:module/LoginModuleTx");
//		} catch (NamingException e1) {
//			throw new IllegalStateException(e1);
//		}
	}

	@Override
	public boolean login() throws LoginException {
		
		// Do not accept requests from http.
		try {
			HttpServletRequest request = (HttpServletRequest) PolicyContext
					.getContext(HttpServletRequest.class.getName());
			if(request!=null) return false;
		} catch (PolicyContextException e) {
			throw new IllegalStateException(e);
		}
		
		Date currentDate = new Date();

			newPrincipal = new TermWsUserPrincipal();
//			newPrincipal.setAccessTime(secTerminal.getAccessTime());
//			newPrincipal.setLocality(secTerminal.getLocality());
			newPrincipal.setLoginName("francis");
			newPrincipal.setLangIso2("en");
			newPrincipal.setLoginTime(currentDate);
//			newPrincipal.setMacAddress(secTerminal.getMacAddress());
//			newPrincipal.setTermCred(termCdtl.getCre());
//			newPrincipal.setTermName(secTerminal.getTermName());
//			newPrincipal.setTermSessionId(secTermSession.getId());
			newPrincipal.setTimeZone("Africa/Douala");
//			newPrincipal.setUserSessionId(secUserSession.getId());
			newPrincipal.setWorkspaceId("A01CM_admin_A01CM");

			return (true);
	}

	@Override
	public boolean commit() throws LoginException {
		if (newPrincipal == null) return false;
		
		/*
		 * The set of principals of this subject. We will add the
		 * SecurityConstants.CALLER_PRINCIPAL_GROUP and the
		 * SecurityConstants.ROLES_GROUP to this set.
		 */
		Set<Principal> principals = subject.getPrincipals();

		
		/*
		 * The user identity.
		 */
		if(!principals.contains(newPrincipal))
			principals.add(newPrincipal);

		// get the Roles group
		Group[] roleSets = getRoleSets();
		for (Group group : roleSets) {
			Group subjectGroup = findGroup(group.getName(), principals);
			if (subjectGroup == null) {
				subjectGroup = new SimpleGroup(group.getName());
				principals.add(subjectGroup);
			}
			// Copy the group members to the Subject group
			Enumeration<? extends Principal> members = group.members();
			while (members.hasMoreElements()) {
				Principal role = (Principal) members.nextElement();
				subjectGroup.addMember(role);
			}
		}
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		newPrincipal = null;
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		newPrincipal = null;
		// Remove all principals and groups of classes defined here.
		Set<Principal> principals = subject.getPrincipals();
		Set<SimplePrincipal> principals2Remove = subject.getPrincipals(SimplePrincipal.class);
		principals.removeAll(principals2Remove);
		Set<TermWsUserPrincipal> principals2Remove2 = subject.getPrincipals(TermWsUserPrincipal.class);
		principals.removeAll(principals2Remove2);
		return true;
	}


	/**
	 * Gets the role sets.
	 * 
	 * @return the role sets
	 */
	private Group[] getRoleSets()
	{
		SimpleGroup simpleGroup = new SimpleGroup(SecurityConstants.ROLES_IDENTIFIER);
		simpleGroup.addMember(new SimplePrincipal("LOGIN"));
		return new Group[] { simpleGroup };
	}

	/**
	 * Find group.
	 * 
	 * @param name
	 *            the name
	 * @param principals
	 *            the principals
	 * @return the group
	 */
	private Group findGroup(String name, Set<Principal> principals)
	{
		for (Principal principal : principals)
		{
			if (!(principal instanceof Group))
				continue;
			Group group = Group.class.cast(principal);
			if (name.equals(group.getName()))
				return group;
		}
		return null;
	}
	
}
