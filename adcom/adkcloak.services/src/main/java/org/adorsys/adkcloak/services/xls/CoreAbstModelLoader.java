package org.adorsys.adkcloak.services.xls;

import java.util.List;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.keycloak.models.KeycloakSession;

public abstract class CoreAbstModelLoader<T> extends CoreAbstLoader<T>{

	private KeycloakSession session;
	
	protected abstract Object getIdentif(T t);
	protected abstract T lookup(Object identif);
	protected abstract T update(T t);
	protected abstract T create(T t);

	public CoreAbstModelLoader(KeycloakSession session) {
		super();
		this.session = session;
	}
	
	@Override
	public T save(T entity, List<PropertyDesc> fields){
		Object identif = getIdentif(entity);
		T found = lookup(identif);
		if(found!=null){
			if(!objectEquals(found, entity, fields)){
				found = update(found);
			}
		} else {
			found = create(entity);
		}
		return found;
	}
	
	

}
