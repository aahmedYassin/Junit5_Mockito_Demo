package com.gizasystems.service;

import org.springframework.stereotype.Service;


@Service
public class EmployeeDataValidation {


    public boolean isValidEmail(String mail) {


        if (!mail.contains("@")) {
            throw new RuntimeException("Employee Email Not Valid zzz@zzz.zzz");

        } else
            return true;
    }

    public boolean isValidAge(double age) {
        if (age > 50)
            throw new RuntimeException("Employee Age Must be Less Than 50 Year");
        else
            return true;

    }

    public boolean isValidSalary(double salary) {
        if (salary < 0) {
            throw new RuntimeException("Employee Salary Must be Greater than Zero");
        } else
            return true;
    }


}
