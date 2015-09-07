package org.adorsys.adkcloak.loader;

import java.util.List;

import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;

public abstract class CoreAbstModelLoader<T> extends CoreAbstLoader<T>{

	protected abstract Object getIdentif(T t);
	protected abstract T lookup(Object identif);
	protected abstract T update(T t);
	protected abstract T create(T t);
	
	@Override
	public T save(T entity, List<PropertyDesc> fields){
		Object identif = getIdentif(entity);
		T found = lookup(identif);
		if(found!=null){
			if(!objectEquals(found, entity, fields)){
				return update(found);
			} else {
				return found;
			}
		} else {
			return create(entity);
		}
	}

}
