/**
 * 
 */
package org.adorsys.adbase.dto;

import java.io.Serializable;

import org.adorsys.adbase.jpa.OrgContact;


/**
 * @author boriswaguia
 *
 */
public class OrgContactDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7341236904575818354L;
	
	private String id;
	private String identif;
	private String contactPeople;
	private String email;
	private String phone;
	private String region;
	private String street;
	private String street2;
	private String zipCode;
	private String subdivision;
	private String division;
	private String country;
	private String fax;
	private String contactIndex;
	
	private String countryName;
	private String ctryIso3;
	private String ctryIdentif;
	private String city;
	
	private String ouIdentif;
	private String ouName;
	
	public OrgContactDto() {}


	public OrgContactDto(OrgContact contact) {
		this.id = contact.getId();
		this.identif = contact.getIdentif();
		this.contactPeople = contact.getContactPeople();
		this.email = contact.getEmail();
		this.city = contact.getCity();
		this.region = contact.getRegion();
		this.phone = contact.getPhone();
		this.street = contact.getStreet();
		this.street2 = contact.getStreet2();
		this.zipCode = contact.getZipCode();
		this.subdivision = contact.getSubdivision();
		this.division = contact.getDivision();
		this.country = contact.getCountry();
		this.fax = contact.getFax();
		this.contactIndex = contact.getContactIndex();
	}
	
	public OrgContactDto(OrgContact contact,String ctryName,String ctryIso3,String ctryIdentif) {
		this(contact);
		this.countryName = ctryName;
		this.ctryIso3 = ctryIso3;
		this.ctryIdentif = ctryIdentif;
	}
	public OrgContactDto(OrgContact contact,String ctryName,String ctryIso3,String ctryIdentif,String ouIdentif,String ouName) {
		this(contact, ctryName, ctryIso3, ctryIdentif);
		this.ouIdentif = ouIdentif;
		this.ouName = ouName;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdentif() {
		return identif;
	}

	public void setIdentif(String identif) {
		this.identif = identif;
	}

	public String getContactPeople() {
		return contactPeople;
	}

	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getCtryIso3() {
		return ctryIso3;
	}

	public void setCtryIso3(String ctryIso3) {
		this.ctryIso3 = ctryIso3;
	}

	public String getCtryIdentif() {
		return ctryIdentif;
	}

	public void setCtryIdentif(String ctryIdentif) {
		this.ctryIdentif = ctryIdentif;
	}

	public String getOuIdentif() {
		return ouIdentif;
	}

	public void setOuIdentif(String ouIdentif) {
		this.ouIdentif = ouIdentif;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactIndex() {
		return contactIndex;
	}

	public void setContactIndex(String contactIndex) {
		this.contactIndex = contactIndex;
	}


	@Override
	public String toString() {
		return "OrgContactDto [id=" + id + ", identif=" + identif
				+ ", contactPeople=" + contactPeople + ", email=" + email
				+ ", phone=" + phone + ", countryName=" + countryName
				+ ", ctryIso3=" + ctryIso3 + ", ctryIdentif=" + ctryIdentif
				+ ", city=" + city + ", region=" + region + ", ouIdentif="
				+ ouIdentif + ", ouName=" + ouName + "]";
	}
}
