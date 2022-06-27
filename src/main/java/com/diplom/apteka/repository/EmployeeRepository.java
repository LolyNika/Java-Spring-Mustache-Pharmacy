package com.diplom.apteka.repository;

import com.diplom.apteka.model.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

//@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

//    @Query("SELECT n FROM Employee n WHERE n.name = ?1")
//    List<Employee> findByTitlePositionalBind(String name);

    @Query("SELECT e FROM Employee e inner join Role r WHERE e.employeeID = ?1")
    List<Employee> getAllRoles(int employeeID);

    Employee findByUsername (String username);

//    @Query("SELECT e FROM Employee e WHERE e.employeeID = ?1")
//    List<Employee> getAllempl(int employeeID);

//    List<Employee> findAllBySurname(String surname);

//    void deleteById(int id);
//
//    Employee findById (int id);
}
