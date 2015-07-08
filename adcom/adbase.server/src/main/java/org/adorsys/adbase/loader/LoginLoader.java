package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class LoginLoader extends AbstractObjectLoader<Login> {

	@Inject
	private LoginEJB ejb;

	@Override
	protected Login newObject() {
		return new Login();
	}

	public Login findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public Login create(Login entity) {
		return ejb.create(entity);
	}

	public Login update(Login found) {
		return ejb.update(found);
	}

	public Login deleteById(String id) {
		return ejb.deleteById(id);
	}

}
