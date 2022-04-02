package com.e3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @Column(length=100, unique = true)
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonManagedReference
    @OrderBy(value="firstName")
    private List<Employee> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id) && name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
