package com.diplom.apteka.web;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Role;
import com.diplom.apteka.repository.EmployeeRepository;
import com.diplom.apteka.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String greeting (
            @RequestParam(value = "username", required = false) String username1,
            @CurrentSecurityContext(expression="authentication?.name") String username,
                            Model model){
        try {
//            Employee employee = employeeService.findByUsername(username1);
            Employee employee = employeeService.findByUsername(username);
            if (employee.getRole().getRoleID() == 1){
                model.addAttribute("admin_panel", true);
//                model.addAttribute("username", username);
            } else if (employee.getRole().getRoleID() == 2){
                model.addAttribute("admin_panel", false);
//                model.addAttribute("username", username);
            }
        } catch (Exception ex) {
            return "login";
        }
        return "index";
    }

    @GetMapping("/main")
    public String main (Model model){

        return "main";
    }

    @GetMapping("/registration")
    public String registration (){

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(Model model, Employee employee){
        Employee employeeFromDB = employeeRepository.findByUsername(employee.getUsername());

        if (employeeFromDB != null){
            model.addAttribute("found_userfromDB", true);
            return "registration";
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Role role = new Role();
        role.setRoleID(2);
        employee.setRole(role);
        employeeRepository.save(employee);

        return "redirect:/login";
    }
}
