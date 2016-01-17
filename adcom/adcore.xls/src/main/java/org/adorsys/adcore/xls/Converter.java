package org.adorsys.adcore.xls;

import org.apache.poi.ss.usermodel.Cell;

public interface Converter {
	public void setProperty(Cell propertyCell, String propertyName, Object target, CellParser cellParser);
}
