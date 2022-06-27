package com.diplom.apteka.web;

import com.diplom.apteka.service.TabletToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TabletToController {

    @Autowired
    private TabletToService tabletToService;
}
