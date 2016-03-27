package org.adorsys.adcore.xls;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public abstract class AbstractObjectConverter<T> implements Converter {

	@Override
	public void setProperty(Cell propertyCell, String propertyName,
			Object target, CellParser cellParser) {
		T val = null;
		if(propertyCell!=null){
			String valStr = cellParser.parseString(propertyCell);
			if(StringUtils.isNotBlank(valStr)){
				val = parse(valStr);
			}
		}
		try {
			PropertyUtils.setProperty(target, propertyName, val);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected abstract T parse(String val);

}
