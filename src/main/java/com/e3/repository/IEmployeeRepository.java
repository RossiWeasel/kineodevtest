package com.e3.repository;

import com.e3.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT employee FROM Employee employee " +
           "WHERE employee.firstName LIKE %?1% " +
                 "OR employee.lastName LIKE %?1%")
    public List<Employee> findEmployeeByName(String name);

    @Query("SELECT employee FROM Employee employee INNER JOIN employee.company company " +
           "WHERE CAST(company.id As string) LIKE %?1% " +
                 "OR company.name LIKE %?1% " +
           "ORDER BY company.name, employee.firstName")
    public List<Employee> findEmployeeByCompany(String searchText);
}
