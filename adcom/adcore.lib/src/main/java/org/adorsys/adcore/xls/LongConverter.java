package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;

@Singleton
public class LongConverter implements Converter {
	
	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		BigDecimal number = cellParser.parseNumber(propertyCell);
		try {
			Long nbr = number!=null?number.longValue():null;
			PropertyUtils.setProperty(target, propertyName, nbr);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

}
