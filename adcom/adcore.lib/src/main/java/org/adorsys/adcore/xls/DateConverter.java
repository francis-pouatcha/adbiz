package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.inject.Singleton;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;

@Singleton
public class DateConverter implements Converter {
	
	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		Date val = cellParser.parseDate(propertyCell, "dd-MM-yyyy HH:mm:ss","dd-MM-yyyy HH:mm","dd-MM-yyyy HH","dd-MM-yyyy","MM/dd/yyyy");
		try {
			PropertyUtils.setProperty(target, propertyName, val);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
}
