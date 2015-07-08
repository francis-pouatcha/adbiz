package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.utils.AuthChars;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

@Entity 
@Table(name="BaseSecTermRegist")
@Description("SecTermRegist_description")
public class SecTermRegist extends SecAbstractTerminal {
	
	private static final long serialVersionUID = 6577344825120438799L;

	@Column(unique=true)
	@Description("SecTermRegist_registrKey_description")
	@NotNull
	private String registrKey;

	@Column
	@Description("SecTermRegist_registrPwd_description")
	@NotNull
	private String registrPwd;
	
	@Column
	@Description("SecTermRegist_ipAddress_description")
	private String ipAddress;

	@PrePersist
	public void prepersist(){
		if(StringUtils.isBlank(registrKey)) registrKey = RandomStringUtils.random(8, AuthChars.chars);
		if(StringUtils.isBlank(registrPwd)) registrPwd = RandomStringUtils.random(8, AuthChars.chars);
		super.prePersist();
	}

	public SecTermRegist() {
		super();
	}

	public SecTermRegist(SecAbstractTerminal d) {
		super(d);
	}

	public String getRegistrPwd() {
		return registrPwd;
	}

	public void setRegistrPwd(String registrPwd) {
		this.registrPwd = registrPwd;
	}

	public String getRegistrKey() {
		return this.registrKey;
	}

	public void setRegistrKey(final String registrKey) {
		this.registrKey = registrKey;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}