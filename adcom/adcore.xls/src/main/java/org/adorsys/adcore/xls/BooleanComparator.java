package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class BooleanComparator implements PropertyComparator{

	private Method getterMethod;
	
	@Override
	public boolean objectEquals(Object src, Object dest, String propertyName) {
		if(src==dest) return true;
		if(src==null) return false;
		
		Method[] methods = src.getClass().getMethods();
		for (Method method : methods) {
			if(StringUtils.containsIgnoreCase(method.getName(), "is" + propertyName) || StringUtils.containsIgnoreCase(method.getName(), "get" + propertyName)){
				if(method.isAccessible()){
					getterMethod = method;
					break;
				}
			}
		}
		
		if(getterMethod!=null){
			try {
				return Objects.equals(getterMethod.invoke(src),getterMethod.invoke(dest));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new IllegalStateException(e);
			}
		}

		return false;
	}

}
