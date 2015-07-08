package adcom.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonObject;

/**
 * @author clovisgakam
 * 
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class SSLClientBuilder {

	private static CloseableHttpClient httpclient = null ;

	public   CloseableHttpClient buildSSLClient() throws Exception {
		if(httpclient!=null)
			return httpclient;
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("security/adcom.jks").getFile());
		FileInputStream instream = new FileInputStream(file);
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		try {
			trustStore.load(instream, "adcom".toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
				.build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1" },
				null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		return httpclient;
	}
}
