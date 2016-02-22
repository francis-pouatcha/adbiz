package org.adorsys.adcore.xls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public abstract class CoreAbstLoader<T extends Object> {
	private static Logger LOG = Logger.getLogger(CoreAbstLoader.class.getSimpleName());

	private XlsConverterFactory xlsConverterFactory = XlsConverterFactory.singleton();
		
	protected abstract T newObject();
	
	protected abstract CoreAbstLoader<T> getLoader();
	protected abstract StepCallback getStepCallback();

	public boolean load(HSSFSheet hssfSheet, String stepIdentifier){
		Iterator<Row> rowIterator = hssfSheet.rowIterator();
		List<PropertyDesc> fields = null;
		if(rowIterator.hasNext()){
			Row headerRow = rowIterator.next();
			fields = prepare(headerRow);
		}
		if(fields==null || fields.isEmpty()) return true;
		CellParser cellParser = new CellParser(hssfSheet.getWorkbook());
		T last = null;
		boolean hadRow = false;
		while(rowIterator.hasNext()){
			hadRow = true;
			Row row = rowIterator.next();
			last = getLoader().update(row, fields, cellParser, stepIdentifier);
			// Return false if the entity is not saved so loading can be cleanly interupted. 
			if(last==null) return false;
		}
		LOG.info("last element : " + last);
		if(hadRow)
			return getLoader().done(last);
		
		return true;
	}
	
	/*
	 * Allows the release of resources.
	 * 
	 * Returns true if the loader should proceed with the following sheets.
	 * 
	 */
	public boolean done(T last){
		if(last==null) return false;
		return true;
	}
	
	public T update(Row row, List<PropertyDesc> fields, CellParser cellParser, String stepIdentifier) {
		T newObject = newObject();
		for (PropertyDesc propertyDesc : fields) {
			propertyDesc.setProperty(row, newObject, cellParser);
		}
		T save = save(newObject, fields);
		if(save==null) return null;
		if(getStepCallback()!=null)getStepCallback().markSynchPoint(stepIdentifier,row.getRowNum());
		return save;
	}

	public abstract T save(T entity, List<PropertyDesc> fields);

	protected List<PropertyDesc> prepare(Row propertyNames){
		T newObject = newObject();
		List<PropertyDesc> resultList = new ArrayList<PropertyDesc>();
		Iterator<Cell> cellIterator = propertyNames.cellIterator();
		int i = -1;
		while(cellIterator.hasNext()){
			i+=1;
			Cell next = cellIterator.next();
			String propertyName = next.getStringCellValue();
			Class<?> propertyType;
			try {
				propertyType = PropertyUtils.getPropertyType(newObject, propertyName);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				throw new IllegalStateException(e);
			}
			if(propertyType==null) {
				try {
					Field field = newObject.getClass().getField(propertyName);
					propertyType = field.getType();
				} catch (NoSuchFieldException | SecurityException e) {
					continue;
				}
			}
			if(propertyType==null) continue;
			String propertyTypeName = propertyType.getName();
			Converter converter = xlsConverterFactory.findConverter(propertyTypeName);
			if(converter==null) continue;
			PropertyDesc propertyDesc = new PropertyDesc(propertyName, i, converter);
			resultList.add(propertyDesc);
		}
		return resultList;
	}

	protected boolean objectEquals(T src, T dest, List<PropertyDesc> fields) {
		// do not compare validFrom and validTo
		for (PropertyDesc propertyDesc : fields) {
			String propertyName = propertyDesc.getName();
			try {
				Object srcProp = PropertyUtils.getProperty(src, propertyName);
				Object destProp = PropertyUtils.getProperty(dest, propertyName);
				if(!ObjectUtils.equals(srcProp, destProp)) return false;
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				throw new IllegalStateException(e);
			}
		}
		return true;
	}
}
