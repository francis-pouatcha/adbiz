package org.adorsys.adcore.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class XlsProcessEJB {

	/*String adcatalDir = "adcom/adcatal/data";
	String adstockDir = "adcom/adstock/data";
	String adinvtryDir = "adcom/adinvtry/data";
	String adprcmtDir = "adcom/adprcmt/data";*/
	
	@SuppressWarnings("rawtypes")
	Map listDir = new HashMap();
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void registerDir(){
		listDir.put(XlsType.ADCATAL.name(), "adcom/adcatal/data/");
		listDir.put(XlsType.ADSTOCK.name(), "adcom/adstock/data/");
		listDir.put(XlsType.ADINVTRY.name(), "adcom/adinvtry/data/");
		listDir.put(XlsType.ADPRCMT.name(), "adcom/adprcmt/data/");
	}

	public Boolean processXls(FlowInfo flowInfo){

		File fileTmp = new File(flowInfo.getFlowFilePath());
		
		return moveFile((String)listDir.get(flowInfo.getXlsType().name()), fileTmp);
	}
	
	public boolean moveFile(String dir, File file){
		if(file.renameTo(new File(dir + file.getName()))){
			return true;
		}else{
			return false;
		}
	}
}

