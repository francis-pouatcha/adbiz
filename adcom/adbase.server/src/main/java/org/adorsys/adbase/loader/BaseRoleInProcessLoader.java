package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseRoleInProcess;
import org.adorsys.adbase.rest.BaseRoleInProcessEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseRoleInProcessLoader extends AbstractEnumLoader<BaseRoleInProcess> {

	@Inject
	private BaseRoleInProcessEJB ejb;

	@Override
	protected BaseRoleInProcess newObject() {
		return new BaseRoleInProcess();
	}

	public BaseRoleInProcess findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseRoleInProcess create(BaseRoleInProcess entity) {
		return ejb.create(entity);
	}

	public BaseRoleInProcess update(BaseRoleInProcess found) {
		return ejb.update(found);
	}

	public BaseRoleInProcess deleteById(String id) {
		return ejb.deleteById(id);
	}

}
