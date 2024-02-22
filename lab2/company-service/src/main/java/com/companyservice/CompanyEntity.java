package com.companyservice;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(schema = "company-schema", name = "companies")
public class CompanyEntity {

    @jakarta.persistence.Id
    @Id
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "ogrn")
    private String ogrn;

    @Column(name = "description")
    private String description;

    @Column(name = "director_id")
    private Integer directorId;


    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

}
