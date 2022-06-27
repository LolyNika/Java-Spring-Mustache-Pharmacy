package com.diplom.apteka.web;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Fabrications;
import com.diplom.apteka.model.Role;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.repository.EmployeeRepository;
import com.diplom.apteka.service.EmployeeService;
import com.diplom.apteka.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 10;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/employers")
    public String getEmployers(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        List<Employee> emplList = null;

        try {
            emplList = employeeService.getAllEmployers(pageNumber, ROW_PER_PAGE);
        } catch (Exception ex){
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }

        long count = employeeService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("employers", emplList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "employee-list";
    }

    @GetMapping(value = "/employers/{employeeID}/info")
    public String getEmployeeById(Model model, @PathVariable Long employeeID) {
        Employee employee = null;
        try {
            employee = employeeService.findById(employeeID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @GetMapping(value = {"/employee/add"})
    public String showAddEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("add", true);
        model.addAttribute("newroleid", true);
        model.addAttribute("employee", employee);

        String defaultLogin = "LocalHost";
        model.addAttribute("newlogin", true);
        model.addAttribute("passOld", false);
        model.addAttribute("defaultLogin", defaultLogin);

        return "employee-edit";
    }

    @PostMapping(value = "/employee/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult
            , Model model, @RequestParam("section") int section) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            if (section == 0){
                String nullSection = "Пожалуйста выберете роль пользователю";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
            }

            String defaultLogin = "LocalHost";
            model.addAttribute("newlogin", true);
            model.addAttribute("defaultLogin", defaultLogin);

            model.addAttribute("newroleid", true);
            model.addAttribute("add", true);
            model.addAttribute("employee", employee);
            model.addAttribute("passOld", false);
            model.addAttribute("errors", true);
            return "employee-edit";

        } else if (section == 0){
            String nullSection = "Пожалуйста выберете роль пользователю";
            model.addAttribute("nullSectionError", true);
            model.addAttribute("nullSection", nullSection);

            String defaultLogin = "LocalHost";
            model.addAttribute("newlogin", true);
            model.addAttribute("defaultLogin", defaultLogin);

            model.addAttribute("newroleid", true);
            model.addAttribute("add", true);
            model.addAttribute("employee", employee);
            model.addAttribute("errors", true);
            model.addAttribute("passOld", false);
            return "employee-edit";
        }

        try {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));

            Role role = new Role();
            role.setRoleID(section);
            employee.setRole(role);
            employeeService.save(employee);
            return "redirect:/employers";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/employee/{employeeID}/edit"})
    public String showEditEmployee(Model model, @PathVariable long employeeID) {
        Employee employee = null;
        try {
            employee = employeeService.findById(employeeID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }

        model.addAttribute("newlogin", false);
        model.addAttribute("add", false);
        model.addAttribute("newroleid", false);
        model.addAttribute("employee", employee);
        model.addAttribute("passOld", true);
        return "employee-edit";
    }

    @PostMapping(value = {"/employee/{employeeID}/edit"})
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult
            , Model model,
                                 @PathVariable long employeeID, @RequestParam("roleID") int roleID,
             @RequestParam(value = "passwordNew", required = false) String passwordNew) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            Role role = new Role();
            role.setRoleID(roleID);
            employee.setRole(role);

            if (StringUtils.isEmpty(employee.getUsername())){
                String defaultLogin = "LocalHost";
                model.addAttribute("newlogin", true);
                model.addAttribute("defaultLogin", defaultLogin);
            }

            model.addAttribute("passOld", true);
            model.addAttribute("newroleid", false);
            model.addAttribute("add", false);
            model.addAttribute("employee", employee);
            model.addAttribute("errors", true);
            return "employee-edit";
        }

        try {

            Employee employee1 = employeeService.findById(employeeID);
            boolean isPasswordMatch = passwordEncoder.matches(employee.getPassword(), employee1.getPassword());

            if (isPasswordMatch){
                Role role = new Role();
                role.setRoleID(roleID);
                employee.setRole(role);
                employee.setPassword(passwordEncoder.encode(passwordNew));
                employee.setEmployeeID(employeeID);
                employeeService.update(employee);
                return "redirect:/employers";
            } else {
                String wrongPass = "Вы ввели не правильный пароль";
                model.addAttribute("wrongPass", wrongPass);
                model.addAttribute("errors", true);
                model.addAttribute("errorPass", true);
                model.addAttribute("passOld", true);
                Role role = new Role();
                role.setRoleID(roleID);
                employee.setRole(role);
                model.addAttribute("add", false);
                model.addAttribute("employee", employee);
                model.addAttribute("newroleid", false);
                return "employee-edit";
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/employee/{employeeID}/delete"})
    public String deleteEmployee(
            Model model, @PathVariable long employeeID) {
        try {
           employeeService.deleteById(employeeID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/employers";
    }

}
