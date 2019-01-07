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

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
