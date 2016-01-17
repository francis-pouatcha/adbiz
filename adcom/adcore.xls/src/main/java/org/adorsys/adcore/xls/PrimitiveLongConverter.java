package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;

public class PrimitiveLongConverter implements Converter {
	
	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		BigDecimal number = cellParser.parseNumber(propertyCell);
		try {
			long nbr = number!=null?number.longValue():0l;
			PropertyUtils.setProperty(target, propertyName, nbr);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}

}
