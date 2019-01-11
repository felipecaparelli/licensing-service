package com.wro.licenses.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wro.licenses.model.License;
import com.wro.licenses.services.LicenseService;
import com.wro.licenses.utils.UserContextHolder;

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);
	
	@Autowired
	private LicenseService licenseService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<License> getLincenses(@PathVariable("organizationId") String organizationId) {
		logger.info("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		return this.licenseService.getLicensesByOrg(organizationId);
	}

	@RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
	public License getLicense(@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		return this.licenseService.getLicense(organizationId, licenseId);
	}

	@RequestMapping(value = "/{licenseId}/{clientType}")
	public License getLicensesWithClient(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId,
			@PathVariable("clientType") String clientType) {
		return this.licenseService.getLicense(organizationId, licenseId, clientType);
	}

}
