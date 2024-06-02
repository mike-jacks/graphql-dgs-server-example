package com.fullstackdev.graphql.eis;

import java.time.LocalDate;

public class SubmittedEmployee {
    private String first_name;
    private String last_name;
    private Gender gender;
    private LocalDate birth_date;
    private LocalDate hire_date;
    private Integer deptId;

    public SubmittedEmployee() {
    }

    public SubmittedEmployee(String first_name, String last_name, Gender gender, LocalDate birth_date, LocalDate hire_date, Integer deptId) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.hire_date = hire_date;
        this.deptId = deptId;
    }

    public SubmittedEmployee(String first_name, String last_name) {
        this(first_name, last_name, null, null, null, null);
    }

    public SubmittedEmployee(String first_name, String last_name, Integer deptId) {
        this(first_name, last_name, null, null, null, deptId);
    }

    public SubmittedEmployee(String first_name, String last_name, Gender gender) {
        this(first_name, last_name, gender, null, null, null);
    }

    public SubmittedEmployee(String first_name, String last_name, LocalDate birth_date) {
        this(first_name, last_name, null, birth_date, null, null);
    }

    public SubmittedEmployee(String first_name, String last_name, LocalDate birth_date, LocalDate hire_date) {
        this(first_name, last_name, null, birth_date, hire_date, null);
    }

    public SubmittedEmployee(String first_name, String last_name, LocalDate hire_date, Boolean isHireDate) {
        this(first_name, last_name, null, null, hire_date, null);
    }

    public SubmittedEmployee(String first_name, String last_name, Gender gender, LocalDate birth_date,
            LocalDate hire_date) {
        this(first_name, last_name, gender, birth_date, hire_date, null);
    }

    /**
     * @return String return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return String return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return Gender return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return LocalDate return the birth_date
     */
    public LocalDate getBirth_date() {
        return birth_date;
    }

    /**
     * @param birth_date the birth_date to set
     */
    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    /**
     * @return LocalDate return the hire_date
     */
    public LocalDate getHire_date() {
        return hire_date;
    }

    /**
     * @param hire_date the hire_date to set
     */
    public void setHire_date(LocalDate hire_date) {
        this.hire_date = hire_date;
    }

    /**
     * @return Integer return the department_id
     */
    public Integer getDepartment_id() {
        return deptId;
    }

    /**
     * @param department_id the department_id to set
     */
    public void setDepartment_id(Integer department_id) {
        this.deptId = department_id;
    }

}
