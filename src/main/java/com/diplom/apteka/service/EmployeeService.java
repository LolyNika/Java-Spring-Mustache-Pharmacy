package com.diplom.apteka.service;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee deleteById(Long id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find User with id: " + id);
        }
        else {
            employeeRepository.deleteById(id);
        }
        return null;
    }

    public Employee findByUsername (String employee){
        return employeeRepository.findByUsername(employee);
    }

    private boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public Employee findById(Long employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }

    public List<Employee> getAllEmployers(int pageNumber, int rowPerPage){
        List<Employee> employersList = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("employeeID").ascending());
        employeeRepository.findAll(sortedByLastUpdateDesc).forEach(employersList::add);
        return employersList;
    }

    public Employee save(Employee employee) throws Exception {
        return employeeRepository.save(employee);
    }

    public void update(Employee employee) throws Exception {
        employeeRepository.save(employee);
    }

    public Long count() {
        return employeeRepository.count();
    }

//    public List<Employee> getEmployers(){
//        return employeeRepository.findAll();
//    }
//
//    public void deleteAll() {employeeRepository.deleteAll();}
//
//    public void deleteById(int id) {
//        employeeRepository.deleteById(id);
//    }
//
//    public Employee findById(int id) {
//        return employeeRepository.findById(id);
//    }
//
//    public Employee addEmployee(Employee employee){
//        return employeeRepository.save(employee);
//    }

}
