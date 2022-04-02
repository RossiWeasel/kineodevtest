package com.e3.service;

import com.e3.model.Company;
import com.e3.model.Employee;
import com.e3.repository.ICompanyRepository;
import com.e3.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    ICompanyRepository companyRepository;

    @Autowired
    IEmployeeRepository employeeRepository;

    public Company findCompanyById(Long id) {
        return companyRepository.getOne(id);
    }

    /**
     * Create a new Company
     * @param company
     * @return
     */
    public Company createCompany(Company company) {
        if (company.getId() != null && companyRepository.exists(company.getId())) {
            throw new ConstraintViolationException("Duplicated id", new HashSet<>());
        } else {
            Company savedCompany = companyRepository.save(company);
            return savedCompany;
        }
    }

    /**
     * Update an existing company
     * @param company
     * @return
     */
    public Company updateCompany(Company company) {
        if (!companyRepository.exists(company.getId())) {
            return null;
        } else {
            Company savedCompany = companyRepository.save(company);
            return savedCompany;
        }
    }

    /**
     * Delete an existing company
     * @param company
     * @return
     */
    public boolean deleteCompany(Company company) {
        if (companyRepository.exists(company.getId())) {
            return false;
        } else {
            companyRepository.delete(company.getId());
            return true;
        }
    }

    /**
     * Get employee count of a selected Company
     * @param companyId
     * @return
     */
    public int getEmployeeCount(Long companyId) {
        return companyRepository.getEmployeeCount(companyId);
    }

    /**
     * Search company by id or name
     * @param searchVal
     * @return
     */
    public List<Company> searchCompany(String searchVal) {
        return companyRepository.findCompanyByNameOrId(searchVal);
    }

    /**
     * Find Employee by id
     * @param id
     * @return
     */
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findOne(id);
    }

    /**
     * Create a new Employee
     * @param employee
     * @return
     */
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.exists(employee.getId())) {
            throw new ConstraintViolationException("Duplicated id", new HashSet<>());
        } else {
            Employee savedEmployee = employeeRepository.save(employee);
            return savedEmployee;
        }
    }

    /**
     * Update an existing Employee
     * @param employee
     * @return
     */
    public Employee updateEmployee(Employee employee) {
        if (!employeeRepository.exists(employee.getId())) {
            return null;
        } else {
            Employee savedEmployee = employeeRepository.save(employee);
            return savedEmployee;
        }
    }

    /**
     * Delete an existing Employee
     * @param employee
     * @return
     */
    public boolean deleteEmployee(Employee employee) {
        if (employeeRepository.exists(employee.getId())) {
            return false;
        } else {
            employeeRepository.delete(employee);
            return true;
        }
    }

    public List<Employee> searchEmployee(String searchVal) {
        return employeeRepository.findEmployeeByName(searchVal);
    }


}
