package com.fullstackdev.graphql.eis;

public class SubmittedDepartment {
    private String dept_name;

    public SubmittedDepartment() {
    }
    
    public SubmittedDepartment(String dept_name) {
        this.dept_name = dept_name;
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

}
