package com.e3.test.service;

import com.e3.entity.Company;
import com.e3.entity.Employee;
import com.e3.service.CompanyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void whenFindByName_thenReturnEmployees() {
        // initialize
        Company company = new Company();
        company.setName("Kineo");
        Company savedCompany = companyService.createCompany(company);

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Huy");
        firstEmployee.setLastName("Nguyen");
        firstEmployee.setCompany(savedCompany);
        companyService.createEmployee(firstEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Michael");
        secondEmployee.setLastName("Phelps");
        secondEmployee.setCompany(savedCompany);
        companyService.createEmployee(secondEmployee);

        // when
        List<Employee> foundEmployees = companyService.searchEmployee("uy");

        // then
        Assert.assertEquals(1, foundEmployees.size());
        Assert.assertEquals("Huy", foundEmployees.get(0).getFirstName());
    }

}
