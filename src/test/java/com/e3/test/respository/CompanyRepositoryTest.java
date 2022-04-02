package com.e3.test.respository;

import com.e3.model.Company;
import com.e3.model.Employee;
import com.e3.repository.ICompanyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest()
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CompanyRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ICompanyRepository companyRepository;

    @Test
    public void whenFindByName_thenReturnCompanies() {
        // initialize
        Company firstCompany = new Company();
        firstCompany.setName("Kineo");
        entityManager.persist(firstCompany);

        Company secondCompany = new Company();
        secondCompany.setName("Klugger");
        entityManager.persist(secondCompany);
        entityManager.flush();

        // when
        List<Company> foundCompanies = companyRepository.findCompanyByNameOrId("K");

        // then
        Assert.assertEquals(2, foundCompanies.size());
        Assert.assertEquals("Kineo", foundCompanies.get(0).getName());
        Assert.assertEquals("Klugger", foundCompanies.get(1).getName());
    }

    @Test
    public void whenFindById_thenReturnCompanies() {
        // initialize
        Company firstCompany = new Company();
        firstCompany.setName("Kineo");
        entityManager.persist(firstCompany);

        Company secondCompany = new Company();
        secondCompany.setName("Klugger");
        entityManager.persist(secondCompany);
        entityManager.flush();

        // when
        List<Company> foundCompanies = companyRepository.findCompanyByNameOrId("1");

        // then
        Assert.assertEquals(1, foundCompanies.size());
        Assert.assertEquals("Kineo", foundCompanies.get(0).getName());
        entityManager.clear();
    }

    @Test
    public void whenCountEmployees_thenReturnNumber() {
        // initialize
        Company firstCompany = new Company();
        firstCompany.setName("Kineo");
        firstCompany = entityManager.persist(firstCompany);

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Huy");
        firstEmployee.setLastName("Nguyen");
        firstEmployee.setCompany(firstCompany);
        entityManager.persist(firstEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Michael");
        secondEmployee.setLastName("Carrick");
        secondEmployee.setCompany(firstCompany);
        entityManager.persist(secondEmployee);
        entityManager.flush();

        // when
        int noOfCompanies = companyRepository.getEmployeeCount(firstCompany.getId());

        // then
        Assert.assertEquals(2, noOfCompanies);
    }
}
