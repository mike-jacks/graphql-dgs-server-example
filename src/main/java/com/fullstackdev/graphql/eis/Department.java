package com.fullstackdev.graphql.eis;

import java.util.List;

public class Department {
    private Integer id;
    private String dept_name;
    private List<Employee> employees;

    public Department(Integer id, String dept_name, List<Employee> employees) {
        this.id = id;
        this.dept_name = dept_name;
        this.employees = employees;
    }

    public Department(Integer id, String dept_name) {
        this.id = id;
        this.dept_name = dept_name;
        this.employees = null;
    }

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the dept_name
     */
    public String getDept_name() {
        return dept_name;
    }

    /**
     * @param dept_name the dept_name to set
     */
    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    /**
     * @return List<Employee> return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
