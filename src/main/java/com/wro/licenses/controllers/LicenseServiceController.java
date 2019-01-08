package com.wro.licenses.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wro.licenses.model.License;
import com.wro.licenses.services.LicenseService;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	@Autowired
	private LicenseService licenseService;

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicenses(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return new License().withId(licenseId).withProductName("Teleco").withLicenseType("Seat")
				.withOrganizationId("TestOrg");
	}

	@RequestMapping(value = "/{licenseId}/{clientType}")
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId,
			@PathVariable("clientType") String clientType) {
		return this.licenseService.getLicense(organizationId, licenseId, clientType);
	}

}
