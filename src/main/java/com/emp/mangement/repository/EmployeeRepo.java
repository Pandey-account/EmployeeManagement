
package com.emp.mangement.repository;


import com.emp.mangement.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EmployeeRepo extends MongoRepository<Employee, String>{
    
}
