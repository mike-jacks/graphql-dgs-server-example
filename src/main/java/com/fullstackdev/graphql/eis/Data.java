package com.fullstackdev.graphql.eis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

public class Data {
        protected static final ArrayList<Department> departments = new ArrayList<>(List.of(
                        new Department(1, "HR"),
                        new Department(2, "Finance"),
                        new Department(3, "Marketing"),
                        new Department(4, "Engineering")));

        protected static final ArrayList<Employee> employees = new ArrayList<>(List.of(
                        new Employee(1, "Sally", "Bateman", Gender.M, LocalDate.of(1980, 12, 11),
                                        LocalDate.of(2014, 12, 2),
                                        departments.stream()
                                                        .filter(department -> department.getDept_name().equals("HR"))
                                                        .findFirst()
                                                        .get()),
                        new Employee(2, "Jessie", "Fraser", Gender.M, LocalDate.of(1981, 11, 2),
                                        LocalDate.of(2016, 8, 12),
                                        departments.stream()
                                                        .filter(department -> department.getDept_name()
                                                                        .equals("Finance"))
                                                        .findFirst()
                                                        .get()),
                        new Employee(3, "Marlon", "Frost", Gender.F, LocalDate.of(1978, 12, 9),
                                        LocalDate.of(2001, 1, 20),
                                        departments.stream().filter(
                                                        department -> department.getDept_name().equals("Engineering"))
                                                        .findFirst().get()),
                        new Employee(4, "Shantelle", "Fitzpatrick", Gender.M, LocalDate.of(1975, 12, 12),
                                        LocalDate.of(2011, 8, 11),
                                        departments.stream()
                                                        .filter(department -> department.getDept_name()
                                                                        .equals("Marketing"))
                                                        .findFirst()
                                                        .get()),
                        new Employee(5, "Hermione", "Zhang", Gender.F, LocalDate.of(1981, 12, 20),
                                        LocalDate.of(2011, 12, 20),
                                        departments.stream()
                                                        .filter(department -> department.getDept_name().equals("HR"))
                                                        .findFirst()
                                                        .get()),
                        new Employee(6, "Sana", "Sinclair", Gender.M, LocalDate.of(1982, 5, 16),
                                        LocalDate.of(2008, 12, 20),
                                        departments.stream()
                                                        .filter(department -> department.getDept_name()
                                                                        .equals("Finance"))
                                                        .findFirst()
                                                        .get()),
                        new Employee(7, "Charlie", "Morrow", Gender.F, LocalDate.of(1980, 8, 15),
                                        LocalDate.of(2007, 12, 20),
                                        departments.stream().filter(
                                                        department -> department.getDept_name().equals("Engineering"))
                                                        .findFirst().get()),
                        new Employee(8, "Samina", "Donovan", Gender.M, LocalDate.of(1980, 12, 21),
                                        LocalDate.of(2005, 12, 20),
                                        departments.stream().filter(
                                                        department -> department.getDept_name().equals("Engineering"))
                                                        .findFirst().get()),
                        new Employee(9, "Hughie", "Huang", Gender.F, LocalDate.of(1980, 12, 12),
                                        LocalDate.of(2005, 12, 20),
                                        departments.stream().filter(
                                                        department -> department.getDept_name().equals("Engineering"))
                                                        .findFirst().get()),
                        new Employee(10, "Firat", "Hanson", Gender.M, LocalDate.of(1975, 2, 2),
                                        LocalDate.of(2004, 12, 20),
                                        departments.stream().filter(
                                                        department -> department.getDept_name().equals("Engineering"))
                                                        .findFirst().get())));

        @PostConstruct
        public void setEmpstoDept() {

                departments.forEach(department -> {
                        department.setEmployees(
                                        employees.stream()
                                                        .filter(employee -> employee.getDepartment().getId()
                                                                        .equals(department.getId()))
                                                        .collect(Collectors.toList()));
                });
        }

}
