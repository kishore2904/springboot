package com.example.springboot.controller;

import com.example.springboot.model.Employee;
import com.example.springboot.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class employeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        try{
            List<Employee> employeeList = new ArrayList<>();
            employeeRepo.findAll().forEach(employeeList::add);

            if(employeeList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employeeList,HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Optional<Employee> employeeOptional = employeeRepo.findById(id);

        if(employeeOptional.isPresent()){
            return new ResponseEntity<>(employeeOptional.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee employeeObj = employeeRepo.save(employee);

        return new ResponseEntity<>(employeeObj,HttpStatus.OK);

    }
    @PostMapping("/updateEmployeeById/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee newEmployeeData){
        Optional<Employee> oldEmployeeData = employeeRepo.findById(id);
        if (oldEmployeeData.isPresent()){
            Employee employee = oldEmployeeData.get();

            employee.setName(newEmployeeData.getName());
            employee.setEmail(newEmployeeData.getEmail());
            employee.setAddress(newEmployeeData.getAddress());
            employee.setGender(newEmployeeData.getGender());
            employee.setAge(newEmployeeData.getAge());

            Employee employeeData = employeeRepo.save((employee));
            return new ResponseEntity<>(employeeData,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id){
        employeeRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
