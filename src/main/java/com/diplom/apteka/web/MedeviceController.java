package com.diplom.apteka.web;

import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.service.MedeviceService;
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

@Controller
public class MedeviceController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MedeviceService medeviceService;

    @RequestMapping(value = "/medevices")
    public String getMedevices(Model model) {

        List<Medevice> medevicesList = null;
        try {
            medevicesList = medeviceService.getAllMedevieces();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }

        model.addAttribute("medevicesList", medevicesList);

        return "medevice-list";
    }

    @GetMapping(value = "/medevices/{medeviceID}/info")
    public String getMedeviceById(Model model, @PathVariable int medeviceID) {
        Medevice medevice = null;
        try {
            medevice = medeviceService.findById(medeviceID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("medevice", medevice);
        return "medevice-info";
    }

    @GetMapping(value = {"/medevices/add"})
    public String showAddMedevice(Model model) {
        Medevice medevice = new Medevice();
        model.addAttribute("add", true);
        model.addAttribute("medevice", medevice);

        return "medevice-edit";
    }

    @PostMapping(value = "/medevices/add")
    public String addMedevice(
            @Valid @ModelAttribute("medevice") Medevice medevice
            , BindingResult bindingResult
            , Model model
    ) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            model.addAttribute("add", true);
            model.addAttribute("medevice", medevice);
            model.addAttribute("errors", true);
            return "medevice-edit";
        }

        try {
            medeviceService.save(medevice);
            return "redirect:/medevices";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/medevices/{medeviceID}/edit"})
    public String showEditMedevice(Model model, @PathVariable int medeviceID) {
        Medevice medevice = null;
        try {
            medevice = medeviceService.findById(medeviceID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("add", false);
        model.addAttribute("medevice", medevice);
        return "medevice-edit";
    }

    @PostMapping(value = {"/medevices/{medeviceID}/edit"})
    public String updateMedevice(
            @Valid @ModelAttribute("medevice") Medevice medevice, BindingResult bindingResult, Model model,
            @PathVariable int medeviceID) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            model.addAttribute("errors", true);
            return "medevice-edit";
        }

        try {
            medevice.setMedeviceID(medeviceID);
            medeviceService.update(medevice);
            return "redirect:/medevices";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/medevices/{medeviceID}/delete"})
    public String deleteMedevice(
            Model model, @PathVariable int medeviceID) {
        try {
            medeviceService.deleteById(medeviceID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/medevices";
    }
}
