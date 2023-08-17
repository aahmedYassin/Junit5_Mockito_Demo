package com.gizasystems.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDtoRequest {

    private String name;

    private double age;

    private String email;

    private double salary;

}
