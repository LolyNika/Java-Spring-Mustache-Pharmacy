package com.diplom.apteka.web;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/home")
    public String homePage(Model model
            , @CurrentSecurityContext(expression="authentication?.name") String username
    ) {

        try {
            Employee employee = employeeService.findByUsername(username);
            if (employee.getRole().getRoleID() == 1){
                model.addAttribute("admin_panel", true);
//                model.addAttribute("username", username);
            } else if (employee.getRole().getRoleID() == 2){
                model.addAttribute("admin_panel", false);
//                model.addAttribute("username", username);
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "error-page";
//            return "login";
        }

        return "index";
    }
}
