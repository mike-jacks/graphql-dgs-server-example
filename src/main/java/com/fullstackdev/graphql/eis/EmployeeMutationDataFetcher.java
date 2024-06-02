package com.fullstackdev.graphql.eis;

import java.util.Optional;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import graphql.GraphQLException;

@DgsComponent
public class EmployeeMutationDataFetcher {

        @DgsMutation
        public Employee createEmployee(@InputArgument SubmittedEmployee employee) {
                Optional<Department> departmentOptional = Data.departments
                                .stream()
                                .filter(dept -> dept.getId().equals(employee.getDepartment_id())).findFirst();
                if (departmentOptional.isPresent()) {
                        Department department = departmentOptional.get();
                        Employee newEmployee = new Employee(Data.employees.size() + 1, employee.getFirst_name(),
                                        employee.getLast_name(), employee.getGender(), employee.getBirth_date(),
                                        employee.getHire_date(),
                                        department);
                        Data.employees.add(newEmployee);
                        return Data.employees.stream().filter(emp -> emp.getId().equals(newEmployee.getId()))
                                        .findFirst()
                                        .get();
                } else {
                        throw new GraphQLException("Department does not exist with id " + employee.getDepartment_id());
                }
        }

        @DgsMutation
        public Department createDepartment(@InputArgument SubmittedDepartment department) {
                Data.departments.stream().filter(dept -> dept.getDept_name().equals(department.getDept_name()))
                                .findFirst()
                                .ifPresent(dept -> {
                                        throw new GraphQLException(
                                                        "Department already exists with name " + dept.getDept_name());
                                });
                Department newDepartment = new Department(Data.departments.size() + 1, department.getDept_name());
                Data.departments.add(newDepartment);
                return Data.departments.stream()
                                .filter(dept -> dept.getDept_name().equals(newDepartment.getDept_name()))
                                .findFirst()
                                .get();

        }

        @DgsMutation
        public Boolean updateEmployeeDepartment(@InputArgument Integer employeeId,
                        @InputArgument Integer departmentId) {
                Optional<Employee> employeeOptional = Data.employees.stream()
                                .filter(emp -> emp.getId().equals(employeeId)).findFirst();
                Optional<Department> departmentOptional = Data.departments.stream()
                                .filter(dept -> dept.getId().equals(departmentId)).findFirst();
                if (employeeOptional.isPresent() && departmentOptional.isPresent()) {
                        employeeOptional.get().setDepartment(departmentOptional.get());
                        return true;
                }
                return false;
        }
}
