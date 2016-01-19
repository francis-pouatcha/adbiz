package org.adorsys.adcore.xls;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractLoader {
	
	/**
	 * This can be changed to another sufix like abc.xls to limit the 
	 * extent of file processed.
	 */
	public static final String EXCEL_SUFFIX = "xls";

	@SuppressWarnings("rawtypes")
	private Map<String, CoreAbstLoader> loaders = new HashMap<String, CoreAbstLoader>();

	String processedSuffix = ".processed";

	public abstract String getDir();
	public abstract String getSuffix();	

	private List<File> listFiles(){
		List<File> result = new ArrayList<File>();
		
		File newFileDir = new File(getDir());
		if(!newFileDir.exists()){
			try {
				newFileDir.mkdirs();
			} catch (Exception ex){
				// Noop
			}
		}
		
		File[] list = newFileDir.listFiles();

		if(list==null) return result;
		String suffix = getSuffix();
		for (File file : list) {
			String fileName = file.getName();
			if(fileName.startsWith(".")) continue;
			if(FilenameUtils.isExtension(fileName, getProcessedSuffix())) continue;
			if(!StringUtils.endsWithIgnoreCase(fileName, suffix)) continue;
			result.add(file);
		}
		
		return result;
	}
	
	public void process() throws Exception {
		List<File> listFiles = listFiles();
		for (File file : listFiles) {
			try {
				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					throw new IllegalStateException(e);
				}
				loadFile(fis);
				IOUtils.closeQuietly(fis);
			} finally {
				file.renameTo(new File(file.getPath() + getProcessedSuffix()));
			}
		}
	}

	public void processSingleFile(String absolutePath){
		File file = new File(absolutePath);
		if(!file.exists()) return;

		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
		loadFile(fis);
		IOUtils.closeQuietly(fis);
	}

	public void loadFile(FileInputStream fis) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				if(sheet==null) continue;
				String sheetName = sheet.getSheetName();
				@SuppressWarnings("rawtypes")
				CoreAbstLoader loader = loaders.get(sheetName);
				if(loader!=null)loader.load(sheet);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	public String getProcessedSuffix() {
		return processedSuffix;
	}

	public void registerLoader(String key, CoreAbstLoader<?> value){
		loaders.put(key, value);
	}

}