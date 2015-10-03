package org.adorsys.adcom.adres.lib;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class KeyCloakTest {

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		KeyCloak keyCloak = new KeyCloak("test", "realmPublicKey", "authServerUrl", true, "secret", false);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(stringWriter, keyCloak);
		String string = stringWriter.toString();
		Assert.assertTrue(StringUtils.containsIgnoreCase(string, keyCloak.getRealm()));
	}

}
