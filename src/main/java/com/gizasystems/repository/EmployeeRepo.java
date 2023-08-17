package com.gizasystems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gizasystems.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE name = :name")
    Integer deleteByName(String name);

    Employee findByName(String name);
}
