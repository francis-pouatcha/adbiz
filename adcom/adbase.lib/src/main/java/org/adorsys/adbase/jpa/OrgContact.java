package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.annotation.Description;
import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

@Entity 
@Table(name="BaseOrgContact")
@Description("OrgContact_description")
public class OrgContact extends CoreAbstIdentifObject {

	private static final long serialVersionUID = 8597854339966447440L;

	@Column
	@Description("OrgContact_ouIdentif_description")
	@NotNull
	private String ouIdentif;

	@Column
	@Description("OrgContact_contactIndex_description")
	private String contactIndex;

	@Column
	@Description("OrgContact_street_description")
	private String street;

	@Column
	@Description("OrgContact_street2_description")
	private String street2;

	@Column
	@Description("OrgContact_zipCode_description")
	private String zipCode;

	@Column
	@Description("OrgContact_city_description")
	private String city;

	@Column
	@Description("OrgContact_subdivision_description")
	private String subdivision;

	@Column
	@Description("OrgContact_division_description")
	private String division;

	@Column
	@Description("OrgContact_region_description")
	private String region;

	@Column
	@Description("OrgContact_country_description")
	private String country;

	@Column
	@Description("OrgContact_phone_description")
	private String phone;

	@Column
	@Description("OrgContact_fax_description")
	private String fax;

	@Column
	@Description("OrgContact_email_description")
	private String email;

	@Column
	@Description("OrgContact_contactPeople_description")
	@Size(max = 256)
	private String contactPeople;

	public String getOuIdentif() {
		return this.ouIdentif;
	}

	public void setOuIdentif(final String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public String getContactIndex() {
		return contactIndex;
	}

	public void setContactIndex(String contactIndex) {
		this.contactIndex = contactIndex;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(final String street2) {
		this.street2 = street2;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getSubdivision() {
		return this.subdivision;
	}

	public void setSubdivision(final String subdivision) {
		this.subdivision = subdivision;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(final String division) {
		this.division = division;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getContactPeople() {
		return this.contactPeople;
	}

	public void setContactPeople(final String contactPeople) {
		this.contactPeople = contactPeople;
	}

	@Override
	protected String makeIdentif() {
		return ouIdentif + contactIndex;
	}
	
	// This method is used for construct an address from a Contact
	public String getAdress(){
		return street+", "+city +", "+region+", "+country;
	}

	@Override
	public String toString() {
		return "OrgContact [ouIdentif=" + ouIdentif + ", contactIndex="
				+ contactIndex + ", street=" + street + ", street2=" + street2
				+ ", zipCode=" + zipCode + ", city=" + city + ", subdivision="
				+ subdivision + ", division=" + division + ", region=" + region
				+ ", country=" + country + ", phone=" + phone + ", fax=" + fax
				+ ", email=" + email + ", contactPeople=" + contactPeople + "]";
	}
}