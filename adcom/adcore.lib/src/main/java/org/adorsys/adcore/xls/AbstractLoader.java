package org.adorsys.adcore.xls;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.adorsys.adcore.utils.RandomMilisec;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class AbstractLoader {
	
	public static final String EXCEL_SUFFIX = ".xls";

	public void process() throws Exception {
		RandomMilisec.doWait();
		// create this directory if not exists
		File newFileDir = new File(getDir());
		if(!newFileDir.exists()){
			try {
				newFileDir.mkdirs();
			} catch (Exception ex){
				// Noop
			}
		}
		
		File[] list = newFileDir.listFiles();
		if(list==null) return;
		for (File file : list) {
			String fileName = file.getName();
			if(!FilenameUtils.isExtension(fileName, "xls")) continue;
			if(FilenameUtils.isExtension(fileName, getProcessedSuffix())) continue;
			if(fileName.startsWith(".")) continue;

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
	
	public void exportTemplate(HSSFWorkbook workbook){
		File newFileDir = new File(getDir());
		if(!newFileDir.exists()){
			try {
				newFileDir.mkdirs();
			} catch (Exception ex){
				// Noop
			}
		}
		File tplDir = new File(newFileDir, "tpl");
		OutputStream stream = null;
		try {
			if(!tplDir.exists()) tplDir.mkdirs();
			stream = new FileOutputStream(new File(tplDir,getFileName()));
			workbook.write(stream);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if(stream!=null)IOUtils.closeQuietly(stream);
		}
	}

	public abstract void loadFile(FileInputStream fis);

	public abstract String getProcessedSuffix();

	public abstract String getDir();
	
	public abstract String getFileName();
}
