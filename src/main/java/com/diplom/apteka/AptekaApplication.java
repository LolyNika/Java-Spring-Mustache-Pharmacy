package com.diplom.apteka;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.repository.EmployeeRepository;
import com.diplom.apteka.repository.TabletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AptekaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AptekaApplication.class, args);
    }

//    @Autowired
//    TabletRepository tabletRepository;

    @Override
    public void run(String... args) throws Exception {

//        tabletRepository.findById(2L);
//        System.out.println("<<<<<<<<<<" +    tabletRepository.findById(2L));

//        employeeRepository.getAllRoles(2);
//        System.out.println(employeeRepository.getAllRoles(2));

//        System.out.println("<<<<<<<<<<<<<<<<<<<<<");
//
//        employeeRepository.getAllempl(2);
//        System.out.println("1111111111111111111"+employeeRepository.getAllempl(2));

//        Employee nikita = new Employee("markevich", "nikita", "valerchj", "bossOfGYM");
//        Employee mark = new Employee("hazanov", "mark", "eretic", "niggerPOS");
//        Employee vadim = new Employee("rudak", "vadim", "otchestvo", "niggerPOS1");
//
//        List<Employee> employers = Arrays.asList(nikita, mark, vadim);
//
//        employeeRepository.saveAll(employers);
//
//        System.out.println("<<<<<<<<<<<<<" + employeeRepository.findAll());
    }
}
