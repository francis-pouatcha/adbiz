package org.adorsys.adcore.env;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOHelpers {

	public static void closeQuietly(FileOutputStream fileOutputStream) {
        try {
            if (fileOutputStream != null) {
            	fileOutputStream.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
	}

	public static void closeQuietly(FileInputStream fileInputStream) {
        try {
            if (fileInputStream != null) {
            	fileInputStream.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
	}
}
