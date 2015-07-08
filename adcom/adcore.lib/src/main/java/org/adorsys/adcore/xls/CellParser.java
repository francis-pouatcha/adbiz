package org.adorsys.adcore.xls;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

public class CellParser {
	
	private FormulaEvaluator objFormulaEvaluator;
	DataFormatter objDefaultFormat = new DataFormatter();
	
	public CellParser(HSSFWorkbook wb) {
		objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
	}

	public String parseString(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
//		if(cellType==Cell.CELL_TYPE_NUMERIC){
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			return cell.getStringCellValue().trim();
//		} else 
		if (cellType==Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue().trim();
//		} else if (cellType==Cell.CELL_TYPE_BOOLEAN){
//			return cell.getStringCellValue().trim();
//			return cell.getStringCellValue().trim();
		} else {
			objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of cell will return string value
		    return objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
		}
	}

	public BigDecimal parseNumber(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		if(cellType==Cell.CELL_TYPE_NUMERIC){
			return new BigDecimal(cell.getNumericCellValue());
		} else if (cellType==Cell.CELL_TYPE_STRING){
			return new BigDecimal(cell.getStringCellValue().trim());
		} else if (cellType==Cell.CELL_TYPE_BOOLEAN){
			boolean b = cell.getBooleanCellValue();
			if(b) return BigDecimal.ONE;
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(cell.getStringCellValue().trim());
		}
	}

	public Boolean parseBoolean(Cell cell) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		if(cellType==Cell.CELL_TYPE_BOOLEAN){
			return cell.getBooleanCellValue();
		} else if (cellType==Cell.CELL_TYPE_STRING){
			return new Boolean(cell.getStringCellValue().trim());
		} else if(cellType==Cell.CELL_TYPE_NUMERIC){
			BigDecimal b = new BigDecimal(cell.getNumericCellValue());
			if(BigDecimal.ZERO.compareTo(b)==0) return false;
			return true;
		} else {
			return new Boolean(cell.getStringCellValue().trim());
		}
	}

	public Date parseDate(Cell cell, String... patterns) {
		if(cell ==null) return null;
		int cellType = -1;
		if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
			cellType = cell.getCachedFormulaResultType();
		} else {
			cellType = cell.getCellType();
		}
		Date val = null;
		if(cellType==Cell.CELL_TYPE_NUMERIC){
			try {
				val = cell.getDateCellValue();
			} catch(RuntimeException ex){
				// noop
				val = new Date(new BigDecimal(cell.getNumericCellValue()).longValue());
			}
		} else if (cellType==Cell.CELL_TYPE_STRING){
			try {
				val = DateUtils.parseDate(cell.getStringCellValue().trim(), patterns);
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
		}  else {
			try {
				val = cell.getDateCellValue();
			} catch(RuntimeException ex){
				// noop
			}
		}
		return val;
	}
}
