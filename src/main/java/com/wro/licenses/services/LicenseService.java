package com.wro.licenses.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wro.licenses.config.ServiceConfig;
import com.wro.licenses.model.License;
import com.wro.licenses.repository.LicenseRepository;

@Service
public class LicenseService {
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	ServiceConfig config;
	
	public License getLicense(String organizationId, String licenseId) {
		License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license.withComment(config.getExampleProperty());
	}
	
	public List<License> getLicensesByOrg(String organizationId) {
		return this.licenseRepository.findByOrganizationId(organizationId);
	}
	
	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		this.licenseRepository.save(license);
	}

}
