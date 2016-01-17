package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class CopyUtils {

	public static void copy(Object dest, Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalStateException(e);
		}
	}
}
