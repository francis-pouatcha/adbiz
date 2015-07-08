package org.adorsys.adbnsptnr.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.adorsys.adcore.xls.AbstractLoader;
import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Singleton
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Startup
public class DataSheetLoader extends AbstractLoader {

	String dataDir = "adcom/adbnsptnr/data";
	String processedSuffix = ".processed";
	
	@SuppressWarnings("rawtypes")
	private Map<String, AbstractObjectLoader> loaders = new HashMap<String, AbstractObjectLoader>();

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
				AbstractObjectLoader loader = loaders.get(sheetName);
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
	
	public void registerLoader(String key, AbstractObjectLoader<?> value){
		loaders.put(key, value);
	}

	@Override
	public String getFileName() {
		return "adbnsptnr.tpl.xls";
	}

}
