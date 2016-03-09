package org.adorsys.adcore.xls;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.adorsys.adcore.env.EnvProperties;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractLoader {

	private static final Logger LOG = Logger.getLogger(AbstractLoader.class.getName());
	
	/**
	 * This can be changed to another sufix like abc.xls to limit the 
	 * extent of file processed.
	 */
	public static final String EXCEL_SUFFIX = "xls";
	public static final String ENV_PROPERTY_NAME_ADCOM_IDP_BOOT_DATASHEET_DIR = "BOOT_DATASHEET_DIR";

	@SuppressWarnings("rawtypes")
	private Map<String, CoreAbstLoader> loaders = new HashMap<String, CoreAbstLoader>();

	String processedSuffix = ".processed";

	public abstract String getDir();

	protected abstract StepCallback getStepCallback();
	
	public List<File> listFiles(){
		List<File> result = new ArrayList<File>();
		
		File newFileDir = new File(getDir());
		LOG.info("Datasheet directory: " + newFileDir.getAbsolutePath());
		if(!newFileDir.exists()){
			LOG.info("Datasheet directory does not exist. Will be created : " + newFileDir.getAbsolutePath());			
			try {
				newFileDir.mkdirs();
			} catch (Exception ex){
				LOG.warning("Datasheet directory can not be created : " + ex.getMessage());			
				// Noop
			}
		}
		File[] list = newFileDir.listFiles();

		if(list==null) return result;
		for (File file : list) {
			LOG.info("Candidate file: " + file.getAbsolutePath());			
			String fileName = file.getName();
			if(fileName.startsWith(".")) {
				LOG.info("Hidden file : " + fileName + " will not be processed.");			
				continue;
			}
			if(FilenameUtils.isExtension(fileName, getProcessedSuffix())) {
				LOG.info("File : " + fileName + " carries " + getProcessedSuffix() + " suffix, will not be processed.");			
				continue;
			}
			if(!StringUtils.endsWithIgnoreCase(fileName, getSuffix())) {
				LOG.info("File : " + fileName + " does not have the required suffix " + getSuffix() + ", will not be processed.");			
				continue;
			}
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
				loadFile(fis, null);
				IOUtils.closeQuietly(fis);
			} finally {
				file.renameTo(new File(file.getPath() + getProcessedSuffix()));
			}
		}
	}

	public boolean processSingleFile(String cntnrIdentif, String absolutePath, String stepIdentifier){
		File file = new File(absolutePath);
		if(!file.exists()) return true;

		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
		try {
			return loadFile(fis, stepIdentifier);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	public boolean loadFile(FileInputStream fis, String stepIdentifier) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				if(sheet==null) continue;
				String sheetName = sheet.getSheetName();
				LOG.info("Processing sheet: " + sheetName);			
				@SuppressWarnings("rawtypes")
				CoreAbstLoader loader = loaders.get(sheetName);
				if(loader!=null){
					boolean continueLoad = loader.load(sheet, stepIdentifier);
					if(!continueLoad) {
						LOG.info("Sheet: " + sheetName + " returned interuption signal. Loading process will be interrupted.");			
						return false;
					} else {
						LOG.info("Sheet: " + sheetName + " successfully loaded.");									
					}
				} else {
					LOG.warning("No loader defined for sheet: " + sheetName + " sheet will not be processed.");			
				}
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
		return true;
	}

	public String getProcessedSuffix() {
		return processedSuffix;
	}
	public String getSuffix(){
		return EXCEL_SUFFIX;
	}	

	public void registerLoader(String key, CoreAbstLoader<?> value){
		loaders.put(key, value);
	}

	protected String getDataRoot(){
		return EnvProperties.getEnvOrSystPropThrowException(ENV_PROPERTY_NAME_ADCOM_IDP_BOOT_DATASHEET_DIR);
	}
}
