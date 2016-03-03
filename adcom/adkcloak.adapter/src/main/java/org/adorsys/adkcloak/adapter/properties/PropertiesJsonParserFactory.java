package org.adorsys.adkcloak.adapter.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.adorsys.adcore.env.EnvProperties;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.util.JsonParserDelegate;

public class PropertiesJsonParserFactory extends MappingJsonFactory {

	private final EnvProperties envProperties;

    public PropertiesJsonParserFactory(EnvProperties envProperties) {
		this.envProperties = envProperties;
	}

	@Override
    protected JsonParser _createJsonParser(byte[] data, int offset, int len, IOContext ctxt) throws IOException {
        JsonParser delegate = super._createJsonParser(data, offset, len, ctxt);
        return new PropertiesAwareJsonParser(delegate, envProperties);
    }

    @Override
    protected JsonParser _createJsonParser(Reader r, IOContext ctxt) throws IOException {
        JsonParser delegate = super._createJsonParser(r, ctxt);
        return new PropertiesAwareJsonParser(delegate, envProperties);
    }

    @Override
    protected JsonParser _createJsonParser(InputStream in, IOContext ctxt) throws IOException {
        JsonParser delegate = super._createJsonParser(in, ctxt);
        return new PropertiesAwareJsonParser(delegate, envProperties);
    }

    public static class PropertiesAwareJsonParser extends JsonParserDelegate {

    	private final EnvProperties envProperties;
        public PropertiesAwareJsonParser(JsonParser d, EnvProperties envProperties) {
            super(d);
            this.envProperties = envProperties;
        }

        @Override
        public String getText() throws IOException {
            String orig = super.getText();
            return envProperties.resolve(orig);
        }
    }
}
