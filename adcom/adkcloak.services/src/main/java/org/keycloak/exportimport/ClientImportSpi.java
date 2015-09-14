package org.keycloak.exportimport;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class ClientImportSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "client-import";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ClientImporter.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ClientImporterFactory.class;
    }
}
