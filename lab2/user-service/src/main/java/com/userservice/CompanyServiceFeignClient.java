package com.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@FeignClient(
        name = "company-service",
        path = "/companies",
        url = "http://localhost:8082"
)
public interface CompanyServiceFeignClient {

    @GetMapping("/exists/{companyId}")
    Boolean existById(@PathVariable("companyId") Integer companyId);

    @GetMapping("/{companyId}/name")
    String getCompanyNameById(@PathVariable("companyId") Integer companyId);

}
