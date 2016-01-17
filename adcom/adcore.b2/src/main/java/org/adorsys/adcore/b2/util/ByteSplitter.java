package org.adorsys.adcore.b2.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for splitting and merging byte array.
 * 
 * @author fpo
 *
 */
public class ByteSplitter {
	
	public static byte[] merge(List<byte[]> content){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for (byte[] bytes : content) {
			try {
				bos.write(bytes);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		return bos.toByteArray();
	}
	
	public static List<byte[]> splitt(byte[] source, int chunksize) {
	    List<byte[]> result = new ArrayList<byte[]>();
	    int start = 0;
	    while (start < source.length) {
	        int end = Math.min(source.length, start + chunksize);
	        result.add(Arrays.copyOfRange(source, start, end));
	        start += chunksize;
	    }

	    return result;
	}	
}
