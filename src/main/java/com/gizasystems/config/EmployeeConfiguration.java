package com.gizasystems.config;

import com.gizasystems.service.EmployeeDataValidation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmployeeConfiguration {


    @Bean
    public EmployeeDataValidation getEmployeeDataValidation() {
        return new EmployeeDataValidation();
    }


}
