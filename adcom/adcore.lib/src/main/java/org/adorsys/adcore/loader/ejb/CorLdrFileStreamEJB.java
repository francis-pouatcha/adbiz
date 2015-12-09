package org.adorsys.adcore.loader.ejb;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.loader.jpa.CorLdrFileStream;
import org.adorsys.adcore.loader.repo.CorLdrFileStreamRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.apache.commons.io.FileUtils;

@Stateless
public class CorLdrFileStreamEJB extends CoreAbstIdentifiedEJB<CorLdrFileStream> {

	private static final int chunkSize = 1024 * 65;

	@Inject
	private CorLdrFileStreamRepo repo;
	
	@Inject
	private CorLdrFileChunkEJB chunkEJB;


	@Override
	protected CoreAbstIdentifRepo<CorLdrFileStream> getRepo() {
		return repo;
	}
	
	public CorLdrFileStream store(String cntnrIdentif, String fileName, File file) throws IOException{
		CorLdrFileStream stream = new CorLdrFileStream();
		stream.setFileName(fileName);
		stream.setCntnrIdentif(cntnrIdentif);
		stream.setIdentif(UUID.randomUUID().toString());
		stream.setValueDt(new Date());
		stream = create(stream);
		
		CorFileSplitter splitter = new CorFileSplitter(file, chunkSize, stream);
		for (CorLdrFileChunk fileChunk : splitter) {
			chunkEJB.create(fileChunk);
		}
		splitter.close();
		return stream;
	}
	
	@Inject
	private CorLdrFileChunkLookup chunkLookup;
	public void load(CorLdrFileStream stream, File file){
		String cntnrIdentif = stream.getIdentif();
		Long countByCntnrIdentif = chunkLookup.countByCntnrIdentif(cntnrIdentif);
		int read = 0;
		int max = 5;
		while(read<countByCntnrIdentif){
			int start = read;
			read+=max;
			List<CorLdrFileChunk> found = chunkLookup.findByCntnrIdentifOderPosition(cntnrIdentif, start, max);
			for (CorLdrFileChunk fileChunk : found) {
				try {
					FileUtils.writeByteArrayToFile(file, fileChunk.getContent(), true);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}

}
