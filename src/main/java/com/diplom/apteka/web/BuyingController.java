package com.diplom.apteka.web;

import com.diplom.apteka.model.Buying;
import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.service.BuyingService;
import com.diplom.apteka.service.MedeviceService;
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
public class BuyingController {

    @Autowired
    private BuyingService buyingService;

    @Autowired
    private TabletService tabletService;

    @Autowired
    private MedeviceService medeviceService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/buyings")
    public String getBuyings(Model model) {

        List<Buying> buyingsList = buyingService.getAllBuying();

        model.addAttribute("buyingsList", buyingsList);

        return "buying-list";
    }

    @GetMapping(value = {"/buyings/add"})
    public String showAddBuying(Model model) {
        Buying buying = new Buying();
        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
        model.addAttribute("add", true);
        model.addAttribute("newfabricid", true);
        model.addAttribute("buying", buying);
        model.addAttribute("tabletList", tabletList);
        model.addAttribute("medeviceList", medeviceList);
        return "buying-edit";
    }

    @GetMapping(value = "/buyings/{buyingID}/info")
    public String getBuyingById(Model model, @PathVariable int buyingID) {
        Buying buying = null;
        try {
            buying = buyingService.findById(buyingID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("buying", buying);
        return "buying-info";
    }

    @RequestMapping(value = "/buyings/activity")
    public String getActivityById(Model model, @RequestParam("activity") int activity,
                                  @RequestParam("buyingID") int buyingID) {
        Buying buying = null;
        try {
            buying = buyingService.findById(buyingID);
            if (activity == 1) {
                String yes = "Активный";
                buying.setIscomplited(yes);
                buyingService.update(buying);
            } else if (activity == 2) {
                String no = "Неактивный";
                buying.setIscomplited(no);
                buyingService.update(buying);
            }
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        return "redirect:/buyings";
    }

    @PostMapping(value = "/buyings/add")
    public String addBuying(
            @Valid @ModelAttribute("buying") Buying buying, BindingResult bindingResult, Model model
            , @RequestParam(name = "sectiontablet", required = false) int sectiontablet
            , @RequestParam(name = "sectionmedevice", required = false) int sectionmedevice) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            if (sectiontablet == 0 && sectionmedevice == 0){
                String nullSection = "Пожалуйства выберите лекарственный препарат или медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
            } else  if (sectiontablet != 0 && sectionmedevice != 0){
                String nullSection = "Пожалуйства выберите ТОЛЬКО ЛИБО лекарственный препарат ЛИБО медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);

                model.addAttribute("add", true);
                model.addAttribute("newfabricid", true);
                model.addAttribute("buying", buying);
                model.addAttribute("errors", true);
            }


            List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
            List<Medevice> medeviceList = medeviceService.getAllMedevieces();
            model.addAttribute("tabletList", tabletList);
            model.addAttribute("medeviceList", medeviceList);

            model.addAttribute("newfabricid", true);

            model.addAttribute("add", true);
            model.addAttribute("buying", buying);
            model.addAttribute("errors", true);
            return "buying-edit";
        }

        try {

            if (sectiontablet == 0 && sectionmedevice == 0){
                String nullSection = "Пожалуйства выберите лекарственный препарат или медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);

                List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                model.addAttribute("add", true);
                model.addAttribute("newfabricid", true);
                model.addAttribute("buying", buying);
                model.addAttribute("tabletList", tabletList);
                model.addAttribute("medeviceList", medeviceList);
                model.addAttribute("errors", true);

                return "buying-edit";
            } else  if (sectiontablet != 0 && sectionmedevice != 0){
                String nullSection = "Пожалуйства выберите ТОЛЬКО ЛИБО лекарственный препарат ЛИБО медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);

                List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                model.addAttribute("add", true);
                model.addAttribute("newfabricid", true);
                model.addAttribute("buying", buying);
                model.addAttribute("tabletList", tabletList);
                model.addAttribute("medeviceList", medeviceList);
                model.addAttribute("errors", true);

                return "buying-edit";
            } else  if (sectiontablet != 0){
                Tablet tablet = new Tablet();
                tablet.setTabletID(sectiontablet);
                buying.setTabletbuying(tablet);
            } else if (sectionmedevice != 0){
                Medevice medevice = new Medevice();
                medevice.setMedeviceID(sectionmedevice);
                buying.setMedevicebuying(medevice);
            }

            if (StringUtils.isEmpty(buying.getComment())){
                String comment = "Комментариев нет";
                buying.setComment(comment);
            }

            String yes = "Активный";
            buying.setIscomplited(yes);

            String timeStamp = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(Calendar.getInstance().getTime());
            buying.setOrderdate(timeStamp);

            String buyingORG = "Umbrella Corporation";
            buying.setBuyingorganiz(buyingORG);

            buyingService.save(buying);
            return "redirect:/buyings";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/buyings/{buyingID}/edit"})
    public String showEditBuying(Model model, @PathVariable int buyingID) {
        Buying buying = null;
        try {
            buying = buyingService.findById(buyingID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("add", false);
        model.addAttribute("buying", buying);
        return "buying-edit";
    }

    @PostMapping(value = {"/buyings/{buyingID}/edit"})
    public String updateBuying(
            @Valid @ModelAttribute("buying") Buying buying, BindingResult bindingResult, Model model,
            @PathVariable int buyingID) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            model.addAttribute("add", false);
            model.addAttribute("buying", buying);
            model.addAttribute("errors", true);
            return "buying-edit";
        }

        try {
            buying.setBuyingID(buyingID);

            if (StringUtils.isEmpty(buying.getComment())){
                String comment = "Комментариев нет";
                buying.setComment(comment);
            }

            Buying buying1 = buyingService.findById(buyingID);
            buying.setOrderdate(buying1.getOrderdate());
            buying.setIscomplited(buying1.getIscomplited());
            buying.setBuyingorganiz(buying1.getBuyingorganiz());
            buying.setTabletbuying(buying1.getTabletbuying());
            buying.setMedevicebuying(buying1.getMedevicebuying());

            buyingService.update(buying);
            return "redirect:/buyings";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/buyings/{buyingID}/delete"})
    public String deleteBuying(
            Model model, @PathVariable int buyingID) {
        try {
            buyingService.deleteById(buyingID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/buyings";
    }
}
