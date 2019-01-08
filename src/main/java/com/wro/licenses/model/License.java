package com.wro.licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

	@Id
	@Column(name = "license_id", nullable = false)
	private String licenseId;

	@Column(name = "organization_id", nullable = false)
	private String organizationId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	private String licenseType;

	private String comments;

	private String organizationName;

	private String contactName;

	private String contactEmail;

	private String contactPhone;

	public License withId(String licenseId) {
		this.licenseId = licenseId;
		return this;
	}

	public License withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public License withLicenseType(String licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public License withOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

	public License withComment(String comments) {
		this.comments = comments;
		return this;
	}

	public License withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}

	public License withContactName(String contactName) {
		this.contactName = contactName;
		return this;
	}

	public License withContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
		return this;
	}

	public License withContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
		return this;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public String getProductName() {
		return productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public String getComments() {
		return comments;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

}
