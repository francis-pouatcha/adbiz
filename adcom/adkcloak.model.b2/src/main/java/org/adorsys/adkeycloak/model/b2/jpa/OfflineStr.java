package org.adorsys.adkeycloak.model.b2.jpa;

public class OfflineStr {
    public static final String offlineToString(boolean offline) {
        return offline ? "OFFLINE" : "ONLINE";
    }

}
