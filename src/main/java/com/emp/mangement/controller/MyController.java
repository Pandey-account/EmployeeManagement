
package com.emp.mangement.controller;

import com.emp.mangement.models.Employee;
import com.emp.mangement.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Employee")
public class MyController {
    @Autowired
    private EmployeeService EmployeeService;
    
    
    @PostMapping("/")
    public String addEmployee(@RequestBody Employee emp){
       String uuid = EmployeeService.addEmployee(emp);
       Optional<Employee> es = EmployeeService.getEmployeeById(emp.getReportsTo());
       EmployeeService.sendNewEmployeeEmail(es.get().getEmail(), emp.getEmployeeName(), emp.getPhoneNumber(), emp.getEmail());
       return "Employee added with Id: "+uuid;
    }
    
    @GetMapping("/")
    public List<Employee> getAllEmployee(){
       List<Employee> emp = EmployeeService.getAllEmployee();
       return emp;
    }
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable String id){
        return EmployeeService.getEmployeeById(id);
        
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id,@RequestBody Employee upEmployee){
          EmployeeService.updateEmployeeById(id, upEmployee);
        return ResponseEntity.ok("Employee with id "+ id + " updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id){
        EmployeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee with id " + id + "deleted successfully");
    }
    @GetMapping("/{id}/manager/{level}")
    public ResponseEntity<Employee>getNthLevelManager(@PathVariable String id,@PathVariable int level){
        Employee manager = EmployeeService.getNthLevelManager(id, level);
        
        if(manager != null){
            return ResponseEntity.ok(manager);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
}
