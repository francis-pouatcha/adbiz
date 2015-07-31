package org.adorsys.adcore.props;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adcore.utils.DateUtil;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Subclasses of this class must be declare singleton.
 * @author francis
 *
 */
public abstract class AbstEntiyProps<T> {

	protected abstract AbstEntiyProps<? super T> getSuperProps();
	
	public abstract Class<T> getEntityClass();
	
	@Inject
	private EntityPropsUtils propsUtils;

	private Map<String, Properties> translations;

	public String i18n(String key, String lang) {	
		try {
			String prop = translations.get(lang).getProperty(key);
			if(prop!=null) return prop;
		} catch (Exception e) {
			// Noops
		}
		if(getSuperProps()!=null && getSuperProps().getEntityClass()!=getEntityClass())// last condition to prevent loops.
			return getSuperProps().i18n(key, lang);
		return key;
	}

	@PostConstruct
	protected void postConstruct(){
		translations = propsUtils.load(getEntityClass(), null);
	}
	
	public List<String> fieldValues(List<String> fieldsName, T entity) {
		List<String> listValues = new ArrayList<String>();
		String value;
		for(String fieldName:fieldsName){
			try {
				Class<?> propertyType = PropertyUtils.getPropertyType(entity, fieldName);
				 String propertyTypeName = propertyType.getName();
				
				 if(propertyTypeName.equals("java.util.Date")){
					 Date dte = (Date) PropertyUtils.getProperty(entity, fieldName);
						value = DateUtil.transform(dte,DateUtil.DATE_FORMAT_SHORT);
						listValues.add(value);
				 }
				 if(propertyTypeName.equals("java.lang.String")){
					 value = (String) PropertyUtils.getProperty(entity, fieldName);					
					 listValues.add(value);
				 }
				 if(propertyTypeName.equals("java.math.BigDecimal")){
					 BigDecimal bgd = (BigDecimal)PropertyUtils.getProperty(entity, fieldName);
						listValues.add(bgd!=null?bgd.toString():"0");
				 }
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new IllegalStateException(e);
			}
		}
		
		return listValues;
	}	
}
