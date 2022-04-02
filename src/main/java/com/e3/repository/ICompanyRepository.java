package com.e3.repository;

import com.e3.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT COUNT(employee) FROM Employee employee WHERE employee.company.id = ?1")
    public int getEmployeeCount(Long companyId);

    @Query("SELECT DISTINCT company " +
            "FROM Company company LEFT JOIN FETCH company.employees employees " +
            "WHERE company.name LIKE %?1% " +
            "OR CAST(company.id as string) LIKE %?1% " +
            "ORDER BY company.name")
    public List<Company> findCompanyByNameOrId(String searchVal);
}
