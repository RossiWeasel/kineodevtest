package com.e3.test.respository;

import com.e3.entity.Company;
import com.e3.entity.Employee;
import com.e3.repository.IEmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest()
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployees() {
        // initialize
        Company company = new Company();
        company.setName("Kineo");
        Company savedCompany = entityManager.persist(company);

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Huy");
        firstEmployee.setLastName("Nguyen");
        firstEmployee.setCompany(savedCompany);
        entityManager.persist(firstEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Michael");
        secondEmployee.setLastName("Phelps");
        secondEmployee.setCompany(savedCompany);
        entityManager.persist(secondEmployee);
        entityManager.flush();

        // when
        List<Employee> foundEmployees = employeeRepository.findEmployee("uy");

        // then
        Assert.assertEquals(1, foundEmployees.size());
        Assert.assertEquals("Huy", foundEmployees.get(0).getFirstName());
    }

    @Test
    public void whenFindByCompany_thenReturnEmployees() {
        // initialize
        Company company = new Company();
        company.setName("Kineo");
        Company savedCompany = entityManager.persist(company);

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Huy");
        firstEmployee.setLastName("Nguyen");
        firstEmployee.setCompany(savedCompany);
        entityManager.persist(firstEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Michael");
        secondEmployee.setLastName("Phelps");
        secondEmployee.setCompany(savedCompany);
        entityManager.persist(secondEmployee);
        entityManager.flush();

        // when
        List<Employee> foundEmployees = employeeRepository.findEmployeeByCompany("Ki");

        // then
        Assert.assertEquals(2, foundEmployees.size());
        Assert.assertEquals("Huy", foundEmployees.get(0).getFirstName());
        Assert.assertEquals("Michael", foundEmployees.get(1).getFirstName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenCreateInvalidEmployee_thenThrowsException() {
        // initialize
        Company company = new Company();
        company.setName("Kineo");
        Company savedCompany = entityManager.persist(company);

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Huy???");
        firstEmployee.setLastName("Nguyen");
        firstEmployee.setCompany(savedCompany);
        employeeRepository.save(firstEmployee);
    }
}
