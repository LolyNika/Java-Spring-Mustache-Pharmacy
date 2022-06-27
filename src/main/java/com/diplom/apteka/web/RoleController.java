package com.diplom.apteka.web;

import com.diplom.apteka.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;
}
