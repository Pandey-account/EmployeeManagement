
package com.emp.mangement.repository;


import com.emp.mangement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepo extends JpaRepository<Employee, String>{
    
}
