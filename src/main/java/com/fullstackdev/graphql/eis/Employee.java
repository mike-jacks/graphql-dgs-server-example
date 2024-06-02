package com.fullstackdev.graphql.eis;

import java.time.LocalDate;

public record Employee(Integer id, String first_name, String last_name, Gender gender, LocalDate birth_date,
        LocalDate hire_date, Department department) {

}
