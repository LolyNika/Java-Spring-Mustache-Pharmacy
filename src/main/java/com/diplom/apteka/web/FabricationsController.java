package com.diplom.apteka.web;

import com.diplom.apteka.model.*;
import com.diplom.apteka.service.EmployeeService;
import com.diplom.apteka.service.FabricationsService;
import com.diplom.apteka.service.TabletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class FabricationsController {

    @Autowired
    private FabricationsService fabricationsService;

    @Autowired
    private TabletService tabletService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/fabrications")
    public String getFabrications(Model model) {

        List<Fabrications> fabricationsList = fabricationsService.getAllFabrications();

        model.addAttribute("fabricationsList", fabricationsList);

        return "fabrications-list";
    }

    @GetMapping(value = {"/fabrications/add"})
    public String showAddFabric(Model model) {
        Fabrications fabrications = new Fabrications();
        List<Tablet> tabletList = tabletService.findByMaking("Да");
        model.addAttribute("add", true);
        model.addAttribute("newfabricid", true);
        model.addAttribute("fabrications", fabrications);
        model.addAttribute("tabletList", tabletList);

        return "fabrications-edit";
    }

    @GetMapping(value = "/fabrications/{fabricationsID}/info")
    public String getFabricById(Model model, @PathVariable int fabricationsID) {
        Fabrications fabrications = null;
        try {
            fabrications = fabricationsService.findById(fabricationsID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("fabrications", fabrications);
        return "fabrications-info";
    }

    @RequestMapping(value = "/fabrications/activity")
    public String getActivityById(Model model, @RequestParam("activity") int activity,
                                  @RequestParam("fabricationsID") int fabricationsID) {
        Fabrications fabrications = null;
        try {
            fabrications = fabricationsService.findById(fabricationsID);
            if (activity == 1) {
                String yes = "Активный";
                fabrications.setIscomplited(yes);
                fabricationsService.update(fabrications);
            } else if (activity == 2){
                String no = "Неактивный";
                fabrications.setIscomplited(no);
                fabricationsService.update(fabrications);
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
//        model.addAttribute("fabrications", fabrications);
        return "redirect:/fabrications";
    }

    @PostMapping(value = "/fabrications/add")
    public String addFabric(@Valid @ModelAttribute("fabrications") Fabrications fabrications, BindingResult bindingResult
            , Model model, @RequestParam(value = "section", required = false) int section) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            if (section == 0){
                String nullSection = "Пожалуйства выберете препарат из которого будет изготавливаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
            }

            List<Tablet> tabletList = tabletService.findByMaking("Да");
            model.addAttribute("tabletList", tabletList);
            model.addAttribute("newfabricid", true);

            model.addAttribute("add", true);
            model.addAttribute("fabrications", fabrications);
            model.addAttribute("errors", true);
            return "fabrications-edit";
        }

        try {
            if (StringUtils.isEmpty(fabrications.getComment())){
                String comment = "Комментариев нет";
                fabrications.setComment(comment);
            }
            String timeStamp = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(Calendar.getInstance().getTime());
            Tablet tablet = new Tablet();

            if (section == 0){
                String nullSection = "Пожалуйства выберете препарат из которого будет изготавливаться";

                List<Tablet> tabletList = tabletService.findByMaking("Да");
                model.addAttribute("tabletList", tabletList);
                model.addAttribute("newfabricid", true);
                model.addAttribute("fabrications", fabrications);

                model.addAttribute("add", true);
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
                model.addAttribute("errors", true);
                return "fabrications-edit";
            } else {
                tablet.setTabletID(section);
            }

            fabrications.setOrderdate(timeStamp);
            fabrications.setTabletfabric(tablet);

            String yes = "Активный";
            fabrications.setIscomplited(yes);

            fabricationsService.save(fabrications);
            return "redirect:/fabrications";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/fabrications/{fabricationsID}/edit"})
    public String showEditFabric(Model model, @PathVariable int fabricationsID) {
        Fabrications fabrications = null;
        try {
            fabrications = fabricationsService.findById(fabricationsID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("add", false);
        model.addAttribute("fabrications", fabrications);
        return "fabrications-edit";
    }

    @PostMapping(value = {"/fabrications/{fabricationsID}/edit"})
    public String updateFabric(
            @Valid @ModelAttribute("fabrications") Fabrications fabrications, BindingResult bindingResult, Model model,
            @PathVariable int fabricationsID) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

//            List<Tablet> tabletList = tabletService.findByMaking("Да");
//            model.addAttribute("tabletList", tabletList);
//            model.addAttribute("newfabricid", true);

            model.addAttribute("add", false);
            model.addAttribute("fabrications", fabrications);
            model.addAttribute("errors", true);
            return "fabrications-edit";
        }

        try {
            fabrications.setFabricationsID(fabricationsID);
            Fabrications fabrications1 = fabricationsService.findById(fabricationsID);
            fabrications.setOrderdate(fabrications1.getOrderdate());
            fabrications.setTabletfabric(fabrications1.getTabletfabric());
            fabrications.setIscomplited(fabrications1.getIscomplited());

            fabricationsService.update(fabrications);
            return "redirect:/fabrications";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/fabrications/{fabricationsID}/delete"})
    public String deleteOrder(
            Model model, @PathVariable int fabricationsID) {
        try {
            fabricationsService.deleteById(fabricationsID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/fabrications";
    }
}
