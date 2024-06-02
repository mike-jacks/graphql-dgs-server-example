package com.fullstackdev.graphql.eis;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;

import graphql.ExecutionResult;

@SpringBootTest(classes = { DgsAutoConfiguration.class, EmployeeQueryDataFetcher.class,
        EmployeeMutationDataFetcher.class, DateScalar.class, Data.class })
public class EmployeeDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    EmployeeQueryDataFetcher employeeQueryDataFetcher;

    @MockBean
    EmployeeMutationDataFetcher employeeMutationDataFetcher;

    @BeforeEach
    public void before() {

        Department hrDepartment = new Department(1, "HR");
        Department finDepartment = new Department(2, "Finance");
        Department mktDepartment = new Department(3, "Marketing");
        Department engDepartment = new Department(4, "Engineering");

        Mockito.when(employeeQueryDataFetcher.employees())
                .thenAnswer(invocation -> List.of(
                        new Employee(1, "Sally", "Bateman", Gender.M, LocalDate.of(1980, 12, 11),
                                LocalDate.of(2014, 12, 2), hrDepartment),
                        new Employee(2, "Jessie", "Fraser", Gender.M, LocalDate.of(1981, 11, 2),
                                LocalDate.of(2016, 8, 12), finDepartment),
                        new Employee(3, "Marlon", "Frost", Gender.F, LocalDate.of(1978, 12, 9),
                                LocalDate.of(2001, 1, 20), engDepartment),
                        new Employee(4, "Mike", "Jacks", Gender.M, LocalDate.of(1985, 3, 16),
                                LocalDate.of(2024, 6, 2), mktDepartment)));

        Mockito.when(employeeQueryDataFetcher.employee(1)).thenAnswer(invocation -> Optional.of(new Employee(1, "Sally",
                "Bateman", Gender.M, LocalDate.of(1980, 12, 11), LocalDate.of(2014, 12, 2), hrDepartment)));

        Mockito.when(employeeMutationDataFetcher.createEmployee(any(SubmittedEmployee.class)))
                .thenAnswer(invocation -> {
                    SubmittedEmployee submittedEmployee = invocation.getArgument(0, SubmittedEmployee.class);
                    System.out.println("Mock createemployee called with: " + submittedEmployee);

                    return new Employee(11, submittedEmployee.getFirst_name(),
                            submittedEmployee.getLast_name(),
                            submittedEmployee.getGender(),
                            submittedEmployee.getBirth_date(),
                            submittedEmployee.getHire_date(),
                            hrDepartment);
                });

    }

    @Test
    public void test_employees() {
        List<String> firstNames = dgsQueryExecutor
                .executeAndExtractJsonPath("{ employees { id first_name last_name } }", "data.employees[*].first_name");
        assertThat(firstNames).contains("Sally", "Jessie", "Marlon");
    }

    @Test
    public void test_employee() {
        String employeeName = dgsQueryExecutor
                .executeAndExtractJsonPath("{ employee(id: 1) {id first_name last_name }}", "data.employee.last_name");
        assertThat(employeeName).isEqualTo("Bateman");
    }

    @Test
    public void test_mock_createEmployee() {
        ExecutionResult createEmployeeResult = dgsQueryExecutor.execute(
                "mutation { createEmployee (employee : {first_name :\"Suresh\" , last_name : \"Gadupu\" , deptId : 1 ,gender : M , hire_date : \"2014-12-02\" , birth_date:\"1980-12-11\" }) {id first_name last_name gender hire_date birth_date } }");

        assertThat(createEmployeeResult.getErrors().isEmpty());

        SubmittedEmployee submittedEmployee = new SubmittedEmployee("Test", "User", Gender.M, LocalDate.of(1980, 1, 1),
                LocalDate.of(2010, 1, 1), 1);
        employeeMutationDataFetcher.createEmployee(submittedEmployee);
        verify(employeeMutationDataFetcher, times(1)).createEmployee(submittedEmployee);
        System.out.println("Mock createEmployee called with: " + submittedEmployee);
    }

}
