package com.e3.controller;

import com.e3.entity.Company;
import com.e3.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public Object getCompany(@PathVariable Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        Map<String, Object> result = new HashMap<>();
        result.put("firstName", company.getName());
        result.put("id", company.getId());
        return result;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object createCompany(@RequestBody Company company) {
        try {
            return companyService.createCompany(company);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Object updateCompany(@RequestBody Company company) {
        Company savedCompany = companyService.updateCompany(company);
        if (savedCompany != null) {
            return savedCompany;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE)
    public Object deleteCompany(@PathVariable Long companyId) {
        Company company = companyService.findCompanyById(companyId);
        if (company != null) {
            if (companyService.getEmployeeCount(companyId) == 0) {
                companyService.deleteCompany(company);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/search/{searchVal}", method = RequestMethod.GET)
    public List<Company> getCompany(@PathVariable String searchVal) {
        return companyService.searchCompany(searchVal);
    }
}
