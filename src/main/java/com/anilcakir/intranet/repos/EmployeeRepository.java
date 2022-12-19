package com.anilcakir.intranet.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anilcakir.intranet.domain.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
