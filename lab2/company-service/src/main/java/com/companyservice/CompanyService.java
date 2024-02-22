package com.companyservice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
private final UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, UserServiceFeignClient userServiceFeignClient) {
        this.companyRepository = companyRepository;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    public boolean doesUserExist(Integer directorId) {
        return userServiceFeignClient.existById(directorId);
    }

    public boolean doesCompanyExist(Integer companyId) {
        return companyRepository.existsById(companyId);
    }

    public List<CompanyDTO> getAllCompanies() {
        List<CompanyEntity> companies = companyRepository.findAll();
        return transformToDTOList(companies);
    }

    public CompanyDTO getCompanyById(Integer companyId) {
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
        return transformToDTO(company);
    }

    public String getCompanyNameByID(Integer companyId) {
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
        return company.getName();
    }

    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = transformToEntity(companyDTO);
        CompanyEntity savedCompany = companyRepository.save(companyEntity);
        if (!doesUserExist(companyDTO.getDirectorId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no user with this id");
        }
        return transformToDTO(savedCompany);
    }


    private CompanyDTO transformToDTO(CompanyEntity company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setName(company.getName());
        companyDTO.setOgrn(company.getOgrn());
        companyDTO.setDescription(company.getDescription());
        companyDTO.setDirectorId(company.getDirectorId());
        companyDTO.setDirectorName(userServiceFeignClient.getUserNameById(company.getDirectorId()));
        return companyDTO;
    }

    private List<CompanyDTO> transformToDTOList(List<CompanyEntity> companies) {
        return companies.stream()
                .map(this::transformToDTO)
                .collect(Collectors.toList());
    }

    private CompanyEntity transformToEntity(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyId(companyDTO.getCompanyId());
        companyEntity.setName(companyDTO.getName());
        companyEntity.setOgrn(companyDTO.getOgrn());
        companyEntity.setDescription(companyDTO.getDescription());
        companyEntity.setDirectorId(companyDTO.getDirectorId());
        return companyEntity;
    }

}