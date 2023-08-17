package com.gizasystems;

import com.gizasystems.service.EmployeeDataValidation;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class EmployeeDataValidationTests {


    @InjectMocks
    EmployeeDataValidation employeeDataValidation;


    @Test
    @DisplayName("Age is Valid,Age Less than 50")
    public void ageIsValid() {


        assertTrue(employeeDataValidation.isValidAge(45));
    }

    @Test
    @DisplayName("Age is not Valid,Age more than 50")
    public void ageNotValid_ThrowRuntTimeException() {


        assertThrows(RuntimeException.class, () -> {
            employeeDataValidation.isValidAge(55);

        });
    }

    @Test
    @DisplayName("Email is Valid,Contain @")
    public void emailIsValid() {


        assertTrue(employeeDataValidation.isValidEmail("ahmed@gmail.com"));
    }

    @Test
    @DisplayName("Email not Valid,not Contain @")
    public void emailNotValid_ThrowRuntTimeException() {


        assertThrows(RuntimeException.class, () -> {
            employeeDataValidation.isValidEmail("ahmedgmail.com");

        });
    }

    @Test
    @DisplayName("Salary is Valid,More than zero")
    public void salaryIsValid() {


        assertTrue(employeeDataValidation.isValidSalary(1234));
    }

    @Test
    @DisplayName("Salary not Valid,Less than zero")
    public void salaryNotValid_ThrowRuntTimeException() {


        assertThrows(RuntimeException.class, () -> {
            employeeDataValidation.isValidSalary(-1234);

        });
    }


}
