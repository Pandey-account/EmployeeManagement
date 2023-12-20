
package com.emp.mangement.service;

import com.emp.mangement.models.Employee;
import com.emp.mangement.repository.EmployeeRepo;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
   private  JavaMailSender javaMailSender;
   
    public String addEmployee(Employee emp){
        
        String uuid = java.util.UUID.randomUUID().toString();
        emp.setId(uuid);
        try {
            employeeRepo.save(emp);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       return uuid;
    }
     public List<Employee>getAllEmployee(){
         return employeeRepo.findAll();
     }
     public Optional<Employee> getEmployeeById(String id){
         return employeeRepo.findById(id);
     }
     
     public void updateEmployeeById(String id,Employee upEmployee){
         
         Optional<Employee>exEmployee = employeeRepo.findById(id);
         exEmployee.ifPresent(emps->{
             emps.setEmployeeName(upEmployee.getEmployeeName());
             emps.setPhoneNumber(upEmployee.getPhoneNumber());
             emps.setEmail(upEmployee.getEmail());
             emps.setReportsTo(upEmployee.getReportsTo());
             emps.setProfileImage(upEmployee.getProfileImage());
            
             employeeRepo.save(emps);
             
         });
         
     }
     
     public void deleteEmployeeById(String id){
         employeeRepo.deleteById(id);
     }
     
     public Employee getNthLevelManager(String id, int level){
    
    Employee emplyoee = employeeRepo.findById(id).orElse(null);
    
    if(emplyoee != null && level > 0){
        return findNthLevelManager(emplyoee,level);
    }
    return null;
  }
     
     private Employee findNthLevelManager(Employee employee,int level){
         String reportsToId = employee.getReportsTo();
         if(reportsToId == null || level == 1){
             return employeeRepo.findById(reportsToId).orElse(null);
         }else{
             return findNthLevelManager(employeeRepo.findById(reportsToId).orElse(null),level-1);
         }
     }

    
     
     
     public void sendNewEmployeeEmail(String managerEmail,String employeeName,String phoneNumber,String email){
         MimeMessage message = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message);
         
         try {
             helper.setTo(managerEmail);
             helper.setSubject("New Employee Added");
             helper.setText("Hello,<br><br>" +employeeName + " will now work under you. Mobile number is " +phoneNumber +
                     " and email is " + email +".<br><br>Click[here](mailTo:" + managerEmail +" ) to open your email client.",true);
             javaMailSender.send(message);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}
