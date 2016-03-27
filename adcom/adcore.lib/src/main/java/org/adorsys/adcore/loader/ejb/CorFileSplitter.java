package org.adorsys.adcore.loader.ejb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.adorsys.adcore.loader.jpa.CorLdrFileChunk;
import org.adorsys.adcore.loader.jpa.CorLdrFileStream;

public class CorFileSplitter implements Iterable<CorLdrFileChunk>{

	private final File file;
	private final int chunkSize;
	private final CorLdrFileStream stream;
	private final RandomAccessFile raf;
	
	public CorFileSplitter(File file, int chunkSize, CorLdrFileStream stream) {
		this.file = file;
		this.chunkSize = chunkSize;
		this.stream = stream;
		try {
			this.raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Iterator<CorLdrFileChunk> iterator() {
		return new CorFileSplittterIterator(file, chunkSize, stream, raf);
	}

	static class CorFileSplittterIterator implements Iterator<CorLdrFileChunk> {

		private final ByteBuffer buffer;
		private final CorLdrFileStream stream;
		private final FileChannel inChannel;
		
		private int position = 0;
		private int readCount = -1;
		private boolean read = false;
		
		public CorFileSplittterIterator(File file, int chunkSize, CorLdrFileStream stream, RandomAccessFile raf) {
			this.stream = stream;
			this.buffer = ByteBuffer.allocate(chunkSize);
			this.inChannel = raf.getChannel();
		}

		@Override
		public boolean hasNext() {
			if(!read){
				try {
					read = true;
					readCount=inChannel.read(buffer);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			return readCount > 0;
		}

		@Override
		public CorLdrFileChunk next() {
			if(readCount<=0) throw new IllegalStateException(new IOException("No bytes to read."));
			read = false;
			buffer.flip();
			CorLdrFileChunk fileChunk = new CorLdrFileChunk();
			fileChunk.setCntnrIdentif(stream.getIdentif());
			fileChunk.setContent(Arrays.copyOf(buffer.array(), readCount));
			fileChunk.setSize(readCount);
			fileChunk.setIdentif(UUID.randomUUID().toString());
			fileChunk.setPosition(position++);
			fileChunk.setValueDt(new Date());
			buffer.clear(); // do something with the data and clear/compact it.
			return fileChunk;
		}
	}
	
	public void close() {
		try {
			raf.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
