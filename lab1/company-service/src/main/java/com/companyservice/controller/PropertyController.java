package com.companyservice.controller;


import com.companyservice.configuration.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

    private final Config config;

    @GetMapping
    public String getTestProperty(){
        return config.getTestProperty();
    }
}
