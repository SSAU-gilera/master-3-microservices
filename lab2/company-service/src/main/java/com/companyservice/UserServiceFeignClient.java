package com.companyservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        path = "/users",
        url = "http://localhost:8081"
)
public interface UserServiceFeignClient {
    @GetMapping("/exists/{userId}")
    Boolean existById(@PathVariable("userId") Integer userId);

    @GetMapping("/{userId}/name")
    String getUserNameById(@PathVariable("userId") Integer userId);
}
