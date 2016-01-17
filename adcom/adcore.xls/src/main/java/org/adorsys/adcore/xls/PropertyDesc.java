package org.adorsys.adcore.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class PropertyDesc {
	private final String name;
	private final int pos;
	private final Converter converter;

	public PropertyDesc(String name, int pos, Converter converter) {
		super();
		this.name = name;
		this.pos = pos;
		this.converter = converter;
	}

	public String getName() {
		return name;
	}

	public int getPos() {
		return pos;
	}
	
	public void setProperty(Row row, Object target, CellParser cellParser){
		Cell cell = row.getCell(pos, Row.RETURN_BLANK_AS_NULL);
		converter.setProperty(cell, name, target, cellParser);
	}
}
