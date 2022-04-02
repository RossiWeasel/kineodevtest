package com.e3.controller;

import com.e3.entity.Company;
import com.e3.entity.Employee;
import com.e3.repository.ICompanyRepository;
import com.e3.repository.IEmployeeRepository;
import com.e3.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long employeeId) {
        return companyService.findEmployeeById(employeeId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object createEmployee(@RequestBody Employee employee) {
        Company company = companyService.findCompanyById(employee.getCompany().getId());
        if (company != null) {
            employee.setCompany(company);
            return companyService.createEmployee(employee);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Object updateEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = companyService.updateEmployee(employee);
        if (savedEmployee != null) {
            return savedEmployee;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
    public Object deleteEmployee(@PathVariable Long employeeId) {
        if (companyService.deleteEmployee(companyService.findEmployeeById(employeeId))) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/search/{searchVal}", method = RequestMethod.GET)
    public Object searchEmployeeByName(@PathVariable String searchVal) {
        List<Employee> employeeList = companyService.searchEmployee(searchVal);
        return employeeList;
    }

    @RequestMapping(value = "/search/company/{searchVal}", method = RequestMethod.GET)
    public Object searchEmployeeByCompany(@PathVariable String searchVal) {
        List<Company> companyList = companyService.searchCompany(searchVal);
        List<List<Employee>> result = new ArrayList<>();
        if (companyList.size() > 0) {
            for (Company company: companyList) {
                if (company.getEmployees() != null && !company.getEmployees().isEmpty()) {
                    result.add(company.getEmployees());
                }
            }
            return result;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
