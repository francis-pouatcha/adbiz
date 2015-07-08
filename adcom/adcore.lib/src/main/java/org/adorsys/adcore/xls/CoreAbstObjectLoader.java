package org.adorsys.adcore.xls;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public abstract class CoreAbstObjectLoader<T extends CoreAbstIdentifObject> {

	@Inject
	private XlsConverterFactory xlsConverterFactory;
		
	protected abstract T newObject();
	protected abstract CoreAbstIdentifiedLookup<T> getLookup();
	protected abstract CoreAbstIdentifiedEJB<T> getEjb();


	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void load(HSSFSheet hssfSheet){
		Iterator<Row> rowIterator = hssfSheet.rowIterator();
		List<PropertyDesc> fields = null;
		if(rowIterator.hasNext()){
			Row headerRow = rowIterator.next();
			fields = prepare(headerRow);
		}
		if(fields==null || fields.isEmpty()) return;
		CellParser cellParser = new CellParser(hssfSheet.getWorkbook());
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			update(row, fields, cellParser);
		}
		done();
	}
	
	/*
	 * Allows the release of resources.
	 */
	protected void done(){
		// noop
	}
	
	protected void update(Row row, List<PropertyDesc> fields, CellParser cellParser) {
		T newObject = newObject();
		for (PropertyDesc propertyDesc : fields) {
			propertyDesc.setProperty(row, newObject, cellParser);
		}
		save(newObject, fields);
	}

	protected void save(T entity, List<PropertyDesc> fields){
		String identif = entity.getIdentif();
		if(StringUtils.isBlank(identif)) return;
		T found = getLookup().findByIdentif(identif);
		if(found!=null){
			if(!objectEquals(found, entity, fields)){
				entity.setId(found.getId());
				getEjb().update(found);
			}
		} else {
			getEjb().create(entity);
		}
	}
	
	public void createTemplate(HSSFWorkbook workbook){
		T newObject = newObject();
		Class<? extends Object> beanKlass = newObject.getClass();
		String sheetName = beanKlass.getSimpleName();
		HSSFSheet hssfSheet = workbook.getSheet(sheetName);
		if(hssfSheet!=null){
			int sheetIndex = workbook.getSheetIndex(hssfSheet);
			workbook.removeSheetAt(sheetIndex);
		}
		hssfSheet = workbook.createSheet(sheetName);
		HSSFRow row = hssfSheet.createRow(0);
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanKlass);
		List<PropertyDescriptor> descList = cleanDescriptors(propertyDescriptors);
		int i = -1;
		for (PropertyDescriptor propertyDescriptor : descList) {
			String fieldName = propertyDescriptor.getName();
			if(!PropertyUtils.isWriteable(newObject, fieldName)) continue;
			i+=1;
			HSSFCell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
			cell.setCellValue(fieldName);
		}
	}
	
	// remove 
	private List<PropertyDescriptor> cleanDescriptors(
			PropertyDescriptor[] propertyDescriptors) {
		List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>();
		PropertyDescriptor identifDescriptor = null;
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String fieldName = propertyDescriptor.getName();
			if("id".equals(fieldName) || 
					"version".equals(fieldName) ||
					"validFrom".equals(fieldName) ||
					"validTo".equals(fieldName) ||
					"class".equals(fieldName)
					) continue;
			
			// First keep away identif
//			if("identif".equals(fieldName)) {
//				identifDescriptor=propertyDescriptor;
//				continue;
//			}
			result.add(propertyDescriptor);
		}
		// take identif if no other field
		if(result.isEmpty()) result.add(identifDescriptor);
		return result ;
	}
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
			if("validFrom".equals(propertyName) || "validTo".equals(propertyName)) continue;
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
