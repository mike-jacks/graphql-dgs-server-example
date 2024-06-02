package com.fullstackdev.graphql.eis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import graphql.GraphQLException;
import jakarta.annotation.PostConstruct;

@DgsComponent
public class EmployeeDataFetcher {

    private final ArrayList<Department> departments = new ArrayList<>(List.of(
            new Department(1, "HR"),
            new Department(2, "Finance"),
            new Department(3, "Marketing"),
            new Department(4, "Engineering")));

    private final ArrayList<Employee> employees = new ArrayList<>(List.of(
            new Employee(1, "Sally", "Bateman", Gender.M, LocalDate.of(1980, 12, 11), LocalDate.of(2014, 12, 2),
                    departments.stream().filter(department -> department.getDept_name().equals("HR")).findFirst()
                            .get()),
            new Employee(2, "Jessie", "Fraser", Gender.M, LocalDate.of(1981, 11, 2), LocalDate.of(2016, 8, 12),
                    departments.stream().filter(department -> department.getDept_name().equals("Finance")).findFirst()
                            .get()),
            new Employee(3, "Marlon", "Frost", Gender.F, LocalDate.of(1978, 12, 9), LocalDate.of(2001, 1, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Engineering"))
                            .findFirst().get()),
            new Employee(4, "Shantelle", "Fitzpatrick", Gender.M, LocalDate.of(1975, 12, 12), LocalDate.of(2011, 8, 11),
                    departments.stream().filter(department -> department.getDept_name().equals("Marketing")).findFirst()
                            .get()),
            new Employee(5, "Hermione", "Zhang", Gender.F, LocalDate.of(1981, 12, 20), LocalDate.of(2011, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("HR")).findFirst()
                            .get()),
            new Employee(6, "Sana", "Sinclair", Gender.M, LocalDate.of(1982, 5, 16), LocalDate.of(2008, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Finance")).findFirst()
                            .get()),
            new Employee(7, "Charlie", "Morrow", Gender.F, LocalDate.of(1980, 8, 15), LocalDate.of(2007, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Engineering"))
                            .findFirst().get()),
            new Employee(8, "Samina", "Donovan", Gender.M, LocalDate.of(1980, 12, 21), LocalDate.of(2005, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Engineering"))
                            .findFirst().get()),
            new Employee(9, "Hughie", "Huang", Gender.F, LocalDate.of(1980, 12, 12), LocalDate.of(2005, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Engineering"))
                            .findFirst().get()),
            new Employee(10, "Firat", "Hanson", Gender.M, LocalDate.of(1975, 2, 2), LocalDate.of(2004, 12, 20),
                    departments.stream().filter(department -> department.getDept_name().equals("Engineering"))
                            .findFirst().get())));

    @PostConstruct
    public void setEmpstoDept() {

        departments.forEach(department -> {
            department.setEmployees(
                    employees.stream().filter(employee -> employee.department().getId().equals(department.getId()))
                            .collect(Collectors.toList()));
        });
    }

    @DgsQuery(field = "employee")
    public Optional<Employee> employee(@InputArgument Integer id) {
        return employees.stream().filter(employee -> employee.id().equals(id)).findFirst();
    }

    @DgsQuery(field = "department")
    public Optional<Department> department(@InputArgument Integer id) {
        return departments.stream()
                .filter(department -> department.getId().equals(id)).findFirst();
    }

    @DgsQuery(field = "employees")
    public List<Employee> employees() {
        return employees;
    }

    @DgsQuery(field = "departments")
    public List<Department> departments() {
        return departments;
    }

    @DgsQuery(field = "getEmployeesByDepartmentId")
    public List<Employee> getEmployeesByDepartmentId(@InputArgument Integer deptId) {
        return employees.stream().filter(employee -> employee.department().getId().equals(deptId))
                .collect(Collectors.toList());
    }

    @DgsQuery(field = "getEmployeesByDepartmentName")
    public List<Employee> getEmployeesByDepartmentName(@InputArgument String deptName) {
        return employees.stream().filter(employee -> employee.department().getDept_name().equals(deptName))
                .collect(Collectors.toList());
    }

    @DgsMutation
    public Employee createEmployee(@InputArgument SubmittedEmployee employee) {
        Optional<Department> departmentOptional = departments
                .stream()
                .filter(dept -> dept.getId().equals(employee.getDepartment_id())).findFirst();
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            Employee newEmployee = new Employee(employees.size() + 1, employee.getFirst_name(),
                    employee.getLast_name(), employee.getGender(), employee.getBirth_date(), employee.getHire_date(),
                    department);
            employees.add(newEmployee);
            return employees.stream().filter(emp -> emp.id().equals(newEmployee.id())).findFirst().get();
        } else {
            throw new GraphQLException("Department does not exist with id " + employee.getDepartment_id());
        }
    }

    @DgsMutation
    public Department createDepartment(@InputArgument SubmittedDepartment department) {
        departments.stream().filter(dept -> dept.getDept_name().equals(department.getDept_name())).findFirst()
                .ifPresent(dept -> {
                    throw new GraphQLException("Department already exists with name " + dept.getDept_name());
                });
        Department newDepartment = new Department(departments.size() + 1, department.getDept_name());
        departments.add(newDepartment);
        return departments.stream().filter(dept -> dept.getDept_name().equals(newDepartment.getDept_name())).findFirst()
                .get();

    }
}
