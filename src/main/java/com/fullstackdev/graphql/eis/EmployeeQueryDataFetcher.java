package com.fullstackdev.graphql.eis;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class EmployeeQueryDataFetcher {

        @DgsQuery(field = "employee")
        public Optional<Employee> employee(@InputArgument Integer id) {
                return Data.employees.stream().filter(employee -> employee.getId().equals(id)).findFirst();
        }

        @DgsQuery(field = "department")
        public Optional<Department> department(@InputArgument Integer id) {
                return Data.departments.stream()
                                .filter(department -> department.getId().equals(id)).findFirst();
        }

        @DgsQuery(field = "employees")
        public List<Employee> employees() {
                return Data.employees;
        }

        @DgsQuery(field = "departments")
        public List<Department> departments() {
                return Data.departments;
        }

        @DgsQuery(field = "getEmployeesByDepartmentId")
        public List<Employee> getEmployeesByDepartmentId(@InputArgument Integer deptId) {
                return Data.employees.stream().filter(employee -> employee.getDepartment().getId().equals(deptId))
                                .collect(Collectors.toList());
        }

        @DgsQuery(field = "getEmployeesByDepartmentName")
        public List<Employee> getEmployeesByDepartmentName(@InputArgument String deptName) {
                return Data.employees.stream()
                                .filter(employee -> employee.getDepartment().getDept_name().equals(deptName))
                                .collect(Collectors.toList());
        }
}
