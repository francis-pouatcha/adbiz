package org.keycloak.services.resources.admin;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.spi.NotAcceptableException;
import org.jboss.resteasy.spi.NotFoundException;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.representations.idm.CertificateRepresentation;
import org.keycloak.services.ErrorResponseException;
import org.keycloak.util.PemUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ClientAttributeCertificateResource {
    
    public static final String PRIVATE_KEY = "private.key";
    public static final String X509CERTIFICATE = "certificate";

    protected RealmModel realm;
    private RealmAuth auth;
    protected ClientModel client;
    protected KeycloakSession session;
    protected AdminEventBuilder adminEvent;
    protected String attributePrefix;
    protected String privateAttribute;
    protected String certificateAttribute;

    public ClientAttributeCertificateResource(RealmModel realm, RealmAuth auth, ClientModel client, KeycloakSession session, String attributePrefix, AdminEventBuilder adminEvent) {
        this.realm = realm;
        this.auth = auth;
        this.client = client;
        this.session = session;
        this.attributePrefix = attributePrefix;
        this.privateAttribute = attributePrefix + "." + PRIVATE_KEY;
        this.certificateAttribute = attributePrefix + "." + X509CERTIFICATE;
        this.adminEvent = adminEvent;
    }

    /**
     *
     * @return
     */
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateRepresentation getKeyInfo() {
        CertificateRepresentation info = new CertificateRepresentation();
        info.setCertificate(client.getAttribute(certificateAttribute));
        info.setPrivateKey(client.getAttribute(privateAttribute));
        return info;
    }

    /**
     *
     * @return
     */
    @POST
    @NoCache
    @Path("generate")
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateRepresentation generate() {
        auth.requireManage();

        CertificateRepresentation info = KeycloakModelUtils.generateKeyPairCertificate(client.getClientId());

        client.setAttribute(privateAttribute, info.getPrivateKey());
        client.setAttribute(certificateAttribute, info.getCertificate());
        
        adminEvent.operation(OperationType.ACTION).resourcePath(session.getContext().getUri()).representation(info).success();

        return info;
    }

    /**
     * Upload certificate and eventually private key
     *
     * @param uriInfo
     * @param input
     * @return
     * @throws IOException
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateRepresentation uploadJks(@Context final UriInfo uriInfo, MultipartFormDataInput input) throws IOException {
        CertificateRepresentation info = getCertFromRequest(uriInfo, input);

        if (info.getPrivateKey() != null) {
            client.setAttribute(privateAttribute, info.getPrivateKey());
        } else if (info.getCertificate() != null) {
            client.removeAttribute(privateAttribute);
        } else {
            throw new ErrorResponseException("certificate-not-found", "Certificate or key with given alias not found in the keystore", Response.Status.BAD_REQUEST);
        }

        if (info.getCertificate() != null) {
            client.setAttribute(certificateAttribute, info.getCertificate());
        }

        adminEvent.operation(OperationType.ACTION).resourcePath(session.getContext().getUri()).representation(info).success();
        return info;
    }

    /**
     * Upload only certificate, not private key
     *
     * @param uriInfo
     * @param input
     * @return
     * @throws IOException
     */
    @POST
    @Path("upload-certificate")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateRepresentation uploadJksCertificate(@Context final UriInfo uriInfo, MultipartFormDataInput input) throws IOException {
        CertificateRepresentation info = getCertFromRequest(uriInfo, input);

        if (info.getCertificate() != null) {
            client.setAttribute(certificateAttribute, info.getCertificate());
        } else {
            throw new ErrorResponseException("certificate-not-found", "Certificate with given alias not found in the keystore", Response.Status.BAD_REQUEST);
        }

        adminEvent.operation(OperationType.ACTION).resourcePath(session.getContext().getUri()).representation(info).success();
        return info;
    }

    private CertificateRepresentation getCertFromRequest(UriInfo uriInfo, MultipartFormDataInput input) throws IOException {
        auth.requireManage();
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");

        String keystoreFormat = uploadForm.get("keystoreFormat").get(0).getBodyAsString();
        String keyAlias = uploadForm.get("keyAlias").get(0).getBodyAsString();
        List<InputPart> keyPasswordPart = uploadForm.get("keyPassword");
        char[] keyPassword = keyPasswordPart != null ? keyPasswordPart.get(0).getBodyAsString().toCharArray() : null;

        List<InputPart> storePasswordPart = uploadForm.get("storePassword");
        char[] storePassword = storePasswordPart != null ? storePasswordPart.get(0).getBodyAsString().toCharArray() : null;
        PrivateKey privateKey = null;
        X509Certificate certificate = null;
        try {
            KeyStore keyStore = null;
            if (keystoreFormat.equals("JKS")) keyStore = KeyStore.getInstance("JKS");
            else keyStore = KeyStore.getInstance(keystoreFormat, "BC");
            keyStore.load(inputParts.get(0).getBody(InputStream.class, null), storePassword);
            try {
                privateKey = (PrivateKey)keyStore.getKey(keyAlias, keyPassword);
            } catch (Exception e) {
                // ignore
            }
            certificate = (X509Certificate)keyStore.getCertificate(keyAlias);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        CertificateRepresentation info = new CertificateRepresentation();
        if (privateKey != null) {
            String privateKeyPem = KeycloakModelUtils.getPemFromKey(privateKey);
            info.setPrivateKey(privateKeyPem);
        }

        if (certificate != null) {
            String certPem = KeycloakModelUtils.getPemFromCertificate(certificate);
            info.setCertificate(certPem);
        }

        return info;
    }


    public static class KeyStoreConfig {
        protected Boolean realmCertificate;
        protected String storePassword;
        protected String keyPassword;
        protected String keyAlias;
        protected String realmAlias;
        protected String format;

        public Boolean isRealmCertificate() {
            return realmCertificate;
        }

        public void setRealmCertificate(Boolean realmCertificate) {
            this.realmCertificate = realmCertificate;
        }

        public String getStorePassword() {
            return storePassword;
        }

        public void setStorePassword(String storePassword) {
            this.storePassword = storePassword;
        }

        public String getKeyPassword() {
            return keyPassword;
        }

        public void setKeyPassword(String keyPassword) {
            this.keyPassword = keyPassword;
        }

        public String getKeyAlias() {
            return keyAlias;
        }

        public void setKeyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
        }

        public String getRealmAlias() {
            return realmAlias;
        }

        public void setRealmAlias(String realmAlias) {
            this.realmAlias = realmAlias;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }
    }

    /**
     *
     * @param config
     * @return
     */
    @POST
    @NoCache
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public byte[] getKeystore(final KeyStoreConfig config) {
        auth.requireView();
        if (config.getFormat() != null && !config.getFormat().equals("JKS") && !config.getFormat().equals("PKCS12")) {
            throw new NotAcceptableException("Only support jks or pkcs12 format.");
        }

        String privatePem = client.getAttribute(privateAttribute);
        String certPem = client.getAttribute(certificateAttribute);
        if (privatePem == null && certPem == null) {
            throw new NotFoundException("keypair not generated for client");
        }
        if (privatePem != null && config.getKeyPassword() == null) {
            throw new ErrorResponseException("password-missing", "Need to specify a key password for jks download", Response.Status.BAD_REQUEST);
        }
        if (config.getStorePassword() == null) {
            throw new ErrorResponseException("password-missing", "Need to specify a store password for jks download", Response.Status.BAD_REQUEST);
        }

        byte[] rtn = getKeystore(config, privatePem, certPem);
        return rtn;
    }

    /**
     * Generate new keypair and certificate and downloads private key into specified keystore format. Only generated certificate is saved in Keycloak DB, but private
     * key is not.
     *
     * @param config
     * @return
     */
    @POST
    @NoCache
    @Path("/generate-and-download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public byte[] generateAndGetKeystore(final KeyStoreConfig config) {
        auth.requireManage();

        if (config.getFormat() != null && !config.getFormat().equals("JKS") && !config.getFormat().equals("PKCS12")) {
            throw new NotAcceptableException("Only support jks or pkcs12 format.");
        }
        if (config.getKeyPassword() == null) {
            throw new ErrorResponseException("password-missing", "Need to specify a key password for jks generation and download", Response.Status.BAD_REQUEST);
        }
        if (config.getStorePassword() == null) {
            throw new ErrorResponseException("password-missing", "Need to specify a store password for jks generation and download", Response.Status.BAD_REQUEST);
        }

        CertificateRepresentation info = KeycloakModelUtils.generateKeyPairCertificate(client.getClientId());
        byte[] rtn = getKeystore(config, info.getPrivateKey(), info.getCertificate());

        client.setAttribute(certificateAttribute, info.getCertificate());
        adminEvent.operation(OperationType.ACTION).resourcePath(session.getContext().getUri()).representation(info).success();
        return rtn;
    }

    private byte[] getKeystore(KeyStoreConfig config, String privatePem, String certPem) {
        try {
            String format = config.getFormat();
            KeyStore keyStore;
            if (format.equals("JKS")) keyStore = KeyStore.getInstance("JKS");
            else keyStore = KeyStore.getInstance(format, "BC");
            keyStore.load(null, null);
            String keyAlias = config.getKeyAlias();
            if (keyAlias == null) keyAlias = client.getClientId();
            if (privatePem != null) {
                PrivateKey privateKey = PemUtils.decodePrivateKey(privatePem);
                X509Certificate clientCert = PemUtils.decodeCertificate(certPem);


                Certificate[] chain =  {clientCert};

                keyStore.setKeyEntry(keyAlias, privateKey, config.getKeyPassword().trim().toCharArray(), chain);
            } else {
                X509Certificate clientCert = PemUtils.decodeCertificate(certPem);
                keyStore.setCertificateEntry(keyAlias, clientCert);
            }


            if (config.isRealmCertificate() == null || config.isRealmCertificate().booleanValue()) {
                X509Certificate certificate = realm.getCertificate();
                if (certificate == null) {
                    KeycloakModelUtils.generateRealmCertificate(realm);
                    certificate = realm.getCertificate();
                }
                String certificateAlias = config.getRealmAlias();
                if (certificateAlias == null) certificateAlias = realm.getName();
                keyStore.setCertificateEntry(certificateAlias, certificate);

            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            keyStore.store(stream, config.getStorePassword().trim().toCharArray());
            stream.flush();
            stream.close();
            byte[] rtn = stream.toByteArray();
            return rtn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
