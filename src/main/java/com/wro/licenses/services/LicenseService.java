package com.wro.licenses.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wro.licenses.clients.OrganizationDiscoveryClient;
import com.wro.licenses.clients.OrganizationFeignClient;
import com.wro.licenses.clients.OrganizationRestTemplateClient;
import com.wro.licenses.config.ServiceConfig;
import com.wro.licenses.model.License;
import com.wro.licenses.model.Organization;
import com.wro.licenses.repository.LicenseRepository;

@Service
public class LicenseService {
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	ServiceConfig config;
	
	@Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
    
    private Organization retrieveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }
	
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

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		Organization org = retrieveOrgInfo(organizationId, clientType);
		return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
	}

}
