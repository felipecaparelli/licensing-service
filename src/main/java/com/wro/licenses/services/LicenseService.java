package com.wro.licenses.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wro.licenses.clients.OrganizationDiscoveryClient;
import com.wro.licenses.clients.OrganizationFeignClient;
import com.wro.licenses.clients.OrganizationRestTemplateClient;
import com.wro.licenses.config.ServiceConfig;
import com.wro.licenses.model.License;
import com.wro.licenses.model.Organization;
import com.wro.licenses.repository.LicenseRepository;
import com.wro.licenses.utils.UserContextHolder;

@Service
public class LicenseService {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
	
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
    
    @HystrixCommand
    private Organization getOrganization(String organizationId) {
    	return organizationRestClient.getOrganization(organizationId);
    }
	
	public License getLicense(String organizationId, String licenseId) {
		License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license.withComment(config.getExampleProperty());
	}
		
	@HystrixCommand(
			fallbackMethod = "buildFallbackLicenseList",
			threadPoolKey = "licenseByOrgThreadPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "30"),
					@HystrixProperty(name = "maxQueueSize", value = "10")
			}
			/*commandProperties= {
					@HystrixProperty(
							name="execution.isolation.thread.timeoutInMilliseconds", 
							value="12000"
					)
			}*/)
	public List<License> getLicensesByOrg(String organizationId) {
		
		logger.debug("LicenseService.getLicensesByOrg  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		
		
		//simulating long call 
		randomlyRunLong();
		
		return this.licenseRepository.findByOrganizationId(organizationId);
	}
	
	@SuppressWarnings("unused")
	private List<License> buildFallbackLicenseList(String organizationId) {
		List<License> fallbackList = new ArrayList<>();
		License license = new License().withId("")
				 .withId("0000000-00-00000")
				 .withOrganizationId( organizationId )
				 .withProductName(
				 "Sorry no licensing information currently available");
		fallbackList.add(license);
		return fallbackList;
	}
	
	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		this.licenseRepository.save(license);
	}

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = this.licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		Organization org = getOrganization(organizationId);
		return license
                .withOrganizationName( org.getName())
                .withContactName( org.getContactName())
                .withContactEmail( org.getContactEmail() )
                .withContactPhone( org.getContactPhone() )
                .withComment(config.getExampleProperty());
	}
	
	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if(randomNum==3) sleep();
	}

	private void sleep() {
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
