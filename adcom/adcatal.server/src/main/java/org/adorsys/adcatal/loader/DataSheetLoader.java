package org.adorsys.adcatal.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.CoreAbstEntityLoader;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Startup
public class DataSheetLoader extends AbstractLoader {

	String dataDir = "adcom/adcatal/data";
	String processedSuffix = ".processed";
	
	@SuppressWarnings("rawtypes")
	private Map<String, CoreAbstEntityLoader> loaders = new HashMap<String, CoreAbstEntityLoader>();

	@Override
	public void loadFile(FileInputStream fis) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				if(sheet==null) continue;
				String sheetName = sheet.getSheetName();
				@SuppressWarnings("rawtypes")
				CoreAbstEntityLoader loader = loaders.get(sheetName);
				if(loader!=null)loader.load(sheet);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	@Override
	public String getProcessedSuffix() {
		return processedSuffix;
	}

	@Override
	public String getDir() {
		return dataDir;
	}
	
	public void registerLoader(String key, CoreAbstEntityLoader<?> value){
		loaders.put(key, value);
	}

	@Override
	public String getFileName() {
		return "adcatal.tpl.xls";
	}

}
