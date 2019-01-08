package com.wro.licenses.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wro.licenses.model.Organization;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {
	
	@RequestMapping(method = RequestMethod.GET,
			value="/v1/organization/{organizationId}",
			consumes = "application/json")
	Organization getOrganization(@PathVariable("organizationId") String organizationId);

}
