package com.companyservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/exists/{companyId}")
    public Boolean doesCompanyExist(@PathVariable("companyId") Integer companyId) {
        return companyService.doesCompanyExist(companyId);
    }


    @GetMapping("/{companyId}")
    public ResponseEntity<String> getCompanyById(@PathVariable("companyId") Integer companyId) {
        try {
            CompanyDTO companyDTO = companyService.getCompanyById(companyId);
            String companyInformation = companyDTO.getCompanyId() + " - " + companyDTO.getName() + " (" + companyDTO.getOgrn() + ") - " + companyDTO.getDirectorName();
            return ResponseEntity.ok(companyInformation);
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }

    @GetMapping("/{companyId}/name")
    public String getCompanyNameByID(@PathVariable("companyId") Integer companyId) {
        try {
            return companyService.getCompanyNameByID(companyId);
        } catch (ResponseStatusException ex) {
            return "not found";
        }
    }


    @PostMapping("/create")
    public ResponseEntity<String> createCompany(@RequestBody CompanyDTO companyDTO) {
        try {
            companyService.createCompany(companyDTO);
            return ResponseEntity.ok("Company successfully created");
        } catch (ResponseStatusException ex) {
            String errorMessage = ex.getStatusCode() + " " + ex.getReason();
            return ResponseEntity.status(ex.getStatusCode()).body(errorMessage);
        }
    }
}