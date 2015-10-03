package org.adorsys.adcom.adres.lib;

import java.io.IOException;
import java.io.StringWriter;

import javax.inject.Inject;

import org.adorsys.adcom.adres.utils.SimpleRunner;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SimpleRunner.class)
public class KeyCloakConfigTest {

	private KeyCloak keycloak;
	
	@Inject
	private KeyCloakConfigResolver configResolver;
	
	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		keycloak = new KeyCloak(configResolver.getResource(), 
				configResolver.getRealmPublicKey(), 
				configResolver.getAuthServerUrl(), 
				configResolver.isExposeToken(), 
				configResolver.getSecret(), configResolver.isPublicClient());

		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		mapper.writeValue(stringWriter, keycloak);
		String string = stringWriter.toString();
		Assert.assertTrue(StringUtils.containsIgnoreCase(string, keycloak.getRealm()));
	}

}
