package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;

@Singleton
public class PrimitiveBooleanConverter implements Converter {
	
	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		Boolean val = cellParser.parseBoolean(propertyCell);
		try {
			boolean b = val!=null?val:false;
			PropertyUtils.setProperty(target, propertyName, b);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
}
