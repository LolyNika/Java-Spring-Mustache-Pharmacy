package com.diplom.apteka.web;

import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.model.Tabletto;
import com.diplom.apteka.service.TabletService;
import com.diplom.apteka.service.TabletToService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TabletController {

    @Autowired
    private TabletService tabletService;

    @Autowired
    private TabletToService tabletToService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 1000;

    @RequestMapping(value = "/tablets")
    public String getTablets(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int pageNumber) {

        List<Tablet> tabletlList = tabletService.getAllTablets(pageNumber, ROW_PER_PAGE);

        long count = tabletService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("tablets", tabletlList);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "tablet-list";
    }

    @GetMapping(value = "/tablets/searching")
    public String getTabletBySection(Model model, @RequestParam("section") int section) {
        List<Tablet> tabletlList = null;
        if (section == 0) {
            return "redirect:/tablets";
        }
        try {
            tabletlList = tabletService.findByIdTablet(section);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
        model.addAttribute("tablets", tabletlList);
        return "tablet-list";
    }

    @GetMapping(value = "/tablets/{tabletID}/info")
    public String getTabletById(Model model, @PathVariable int tabletID) {
        Tablet tablet = null;
        try {
            tablet = tabletService.findById(tabletID);
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
        model.addAttribute("tablet", tablet);
        return "tablet-info";
    }

    @GetMapping(value = {"/tablets/add"})
    public String showAddTablet(Model model) {
        Tablet tablet = new Tablet();
        List<Tabletto> tablettoList = null;
        try {
            tablettoList = tabletToService.getAllTabletTo();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
        model.addAttribute("add", true);
        model.addAttribute("tablet", tablet);
        model.addAttribute("tablettoList", tablettoList);

        return "tablet-edit";
    }

    @PostMapping(value = "/tablets/add")
    public String addUser(@Valid @ModelAttribute("tablet") Tablet tablet, BindingResult bindingResult,  Model model
            , @RequestParam("tablettoID") int tablettoID
            , @RequestParam("section") int section) {

        if (bindingResult.hasErrors()){

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            List<Tabletto> tablettoList = null;
            try {
                tablettoList = tabletToService.getAllTabletTo();
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                logger.error(errorMessage);
                model.addAttribute("errorMessage", errorMessage);

//                model.addAttribute("add", false);
                return "error-page";
            }
            model.addAttribute("add", true);
            model.addAttribute("tablet", tablet);
            model.addAttribute("tablettoList", tablettoList);
            model.addAttribute("errors", true);
            return "tablet-edit";
        }

        try {
            Tabletto tabletto = new Tabletto();
            tabletto.setTablettoid(tablettoID);
            tablet.setTabletTo(tabletto);
            if (section == 1) {
                String yes = "Да";
                tablet.setMaking(yes);
            } else if (section == 2) {
                String no = "Нет";
                tablet.setMaking(no);
            }
            tabletService.save(tablet);
            return "redirect:/tablets";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
    }

    @GetMapping(value = {"/tablets/{tabletID}/edit"})
    public String showEditTablet(Model model, @PathVariable int tabletID) {
        Tablet tablet = null;
        List<Tabletto> tablettoList = null;
        Tabletto tabletto = null;
        try {
            tablet = tabletService.findById(tabletID);
            tabletto = tabletToService.findById(tablet.getTabletTo().getTablettoid());
            tablettoList = tabletToService.getAllTabletTo();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
        model.addAttribute("add", false);
        model.addAttribute("tablet", tablet);
        model.addAttribute("tabletto", tabletto);
        model.addAttribute("tablettoList", tablettoList);
        return "tablet-edit";
    }

    @PostMapping(value = {"/tablets/{tabletID}/edit"})
    public String updateTablet(@Valid @ModelAttribute("tablet") Tablet tablet, BindingResult bindingResult,  Model model,
                               @PathVariable int tabletID,
                               @RequestParam("tablettoID") int tablettoID,
                               @RequestParam("section") int section) {

        if (bindingResult.hasErrors()){

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

//            Map<String, String> errorsMaps = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errorsMaps", errorsMaps);

            List<Tabletto> tablettoList = null;
            Tabletto tabletto = null;
            try {
                tablet = tabletService.findById(tabletID);
                tabletto = tabletToService.findById(tablet.getTabletTo().getTablettoid());
                tablettoList = tabletToService.getAllTabletTo();
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                logger.error(errorMessage);
                model.addAttribute("errorMessage", errorMessage);

//                model.addAttribute("add", false);
                return "error-page";
            }
            model.addAttribute("tabletto", tabletto);
            model.addAttribute("tablettoList", tablettoList);
            model.addAttribute("errors", true);
            return "tablet-edit";
        }

        try {
            Tabletto tabletto = new Tabletto();
            tabletto.setTablettoid(tablettoID);
            tablet.setTabletID(tabletID);
            tablet.setTabletTo(tabletto);
            if (section == 1) {
                String yes = "Да";
                tablet.setMaking(yes);
            } else if (section == 2) {
                String no = "Нет";
                tablet.setMaking(no);
            }
            tabletService.update(tablet);
            return "redirect:/tablets";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
    }

    @GetMapping(value = {"/tablets/{tabletID}/delete"})
    public String deleteEmployee(
            Model model, @PathVariable int tabletID) {
        try {
            tabletService.deleteById(tabletID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

//            model.addAttribute("add", false);
            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/tablets";
    }

}
