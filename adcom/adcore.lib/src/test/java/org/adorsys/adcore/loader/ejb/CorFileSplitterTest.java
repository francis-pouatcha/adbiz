package org.adorsys.adcore.loader.ejb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import junit.framework.Assert;

public class CorFileSplitterTest {

	@Test
	public void test() throws IOException {
		File inFile = new File("target/sample.xls");
		InputStream is = CorFileSplitterTest.class.getClassLoader().getResourceAsStream("sample.xls");
//		FileUtils.copyInputStreamToFile(is, inFile);
		IOUtils.copy(is, new FileOutputStream(inFile));
		IOUtils.closeQuietly(is);
		CorLdrFileStream stream = newFileStream(inFile.getName());
		CorFileSplitter splitter = new CorFileSplitter(inFile, 1024*65, stream);
		List<CorLdrFileChunk> list = new ArrayList<CorLdrFileChunk>();
		for (CorLdrFileChunk fileChunk : splitter) {
			list.add(fileChunk);
		}
		splitter.close();
		
		File outFIle = new File("target/sample2.xls");
		for (CorLdrFileChunk corLdrFileChunk : list) {
			FileUtils.writeByteArrayToFile(outFIle, corLdrFileChunk.getContent(), true);			
		}
		
		boolean contentEquals = FileUtils.contentEquals(inFile, outFIle);
		Assert.assertTrue(contentEquals);
	}
	
	private CorLdrFileStream newFileStream(String fileName){
		CorLdrFileStream stream = new CorLdrFileStream();
		stream.setFileName(fileName);
		stream.setCntnrIdentif(UUID.randomUUID().toString());
		stream.setIdentif(UUID.randomUUID().toString());
		stream.setValueDt(new Date());
		return stream;
	}

}
