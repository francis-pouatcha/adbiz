package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;

@Singleton
public class DoubleConverter implements Converter {
	
	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		BigDecimal number = cellParser.parseNumber(propertyCell);
		try {
			PropertyUtils.setProperty(target, propertyName, number.doubleValue());
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

}
