package org.adorsys.adcom.adres.lib;

import javax.inject.Named;

@Named
public class DummyKeyCloakConfigResolver implements KeyCloakConfigResolver {

	@Override
	public String getResource() {
		return "adcatal.client";
	}

	@Override
	public String getRealmPublicKey() {
		return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrVrCuTtArbgaZzL1hvh0xtL5mc7o0NqPVnYXkLvgcwiC3BjLGw1tGEGoJaXDuSaRllobm53JBhjx33UNv+5z/UMG4kytBWxheNVKnL6GgqlNabMaFfPLPCF8kAgKnsi79NMo+n6KnSY8YeUmec/p2vjO2NjsSAVcWEQMVhJ31LwIDAQAB";
	}

	@Override
	public String getAuthServerUrl() {
		return "/auth";
	}

	@Override
	public boolean isExposeToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSecret() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isPublicClient(){
		return true;
	}

}
