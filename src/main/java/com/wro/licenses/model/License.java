package com.wro.licenses.model;

public class License {
	
	private String licenseId;
	private String productName;
	private String licenseType;
	private String organizationId;

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

	public String getLicenseId() {
		return licenseId;
	}

	public String getProductName() {
		return productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

}
