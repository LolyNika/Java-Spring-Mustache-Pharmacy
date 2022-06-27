package com.diplom.apteka.web;

import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.model.Warehouse;
import com.diplom.apteka.service.MedeviceService;
import com.diplom.apteka.service.TabletService;
import com.diplom.apteka.service.WareHouseService;
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
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private TabletService tabletService;

    @Autowired
    private MedeviceService medeviceService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/warehouse")
    public String getWareHouse(Model model) {

        List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

        model.addAttribute("wareHousesList", wareHousesList);

        return "warehouse-list";
    }

    @GetMapping(value = {"/warehouse/add"})
    public String showAddWareHouse(Model model) {
        Warehouse warehouse = new Warehouse();
        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
        model.addAttribute("add", true);
        model.addAttribute("newfabricid", true);
        model.addAttribute("warehouse", warehouse);
        model.addAttribute("tabletList", tabletList);
        model.addAttribute("medeviceList", medeviceList);
        return "warehouse-edit";
    }

    @PostMapping(value = "/warehouse/add")
    public String addWareHouse(
            @Valid @ModelAttribute("warehouse") Warehouse warehouse, BindingResult bindingResult, Model model
            , @RequestParam(name = "sectiontablet", required = false) int sectiontablet
            , @RequestParam(name = "sectionmedevice", required = false) int sectionmedevice) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            if (sectiontablet != 0){
                try {
                    Warehouse warehousetablet = wareHouseService.findByTabletwarehouse_TabletID(sectiontablet);

                    if (sectiontablet == warehousetablet.getTabletwarehouse().getTabletID()) {

                        model.addAttribute("warehouse", warehouse);
                        model.addAttribute("add", true);
                        model.addAttribute("newfabricid", true);
                        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                        model.addAttribute("tabletList", tabletList);
                        model.addAttribute("medeviceList", medeviceList);
                        model.addAttribute("errors", true);

                        String sameError1 = "Данный товар уже на складе";
                        model.addAttribute("sameError", true);
                        model.addAttribute("sameError1", sameError1);
                        return "warehouse-edit";
                    }
                } catch (Exception ignored){
                }

            } else if (sectionmedevice != 0) {
                try {
                    Warehouse warehousemedevice = wareHouseService.findByMedevicewarehouse_MedeviceID(sectionmedevice);

                    if (sectionmedevice == warehousemedevice.getMedevicewarehouse().getMedeviceID()) {

                        model.addAttribute("warehouse", warehouse);
                        model.addAttribute("add", true);
                        model.addAttribute("newfabricid", true);
                        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                        model.addAttribute("tabletList", tabletList);
                        model.addAttribute("medeviceList", medeviceList);
                        model.addAttribute("errors", true);

                        String sameError1 = "Данный товар уже на складе";
                        model.addAttribute("sameError", true);
                        model.addAttribute("sameError1", sameError1);
                        return "warehouse-edit";
                    }
                } catch (Exception ignored){
                }
            }

            if (sectiontablet == 0 && sectionmedevice == 0) {
                String nullSection = "Пожалуйства выберите лекарственный препарат или медицинский прибор, который будет добавляться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
            } else if (sectiontablet != 0 && sectionmedevice != 0) {
                String nullSection = "Пожалуйства выберите ТОЛЬКО ЛИБО лекарственный препарат ЛИБО медицинский прибор, который будет добавляться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);
            }

            List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
            List<Medevice> medeviceList = medeviceService.getAllMedevieces();
            model.addAttribute("tabletList", tabletList);
            model.addAttribute("medeviceList", medeviceList);


            model.addAttribute("add", true);
            model.addAttribute("newfabricid", true);
            model.addAttribute("warehouse", warehouse);
            model.addAttribute("errors", true);
            return "warehouse-edit";
        }

        try {

            if (sectiontablet != 0){
                try {
                    Warehouse warehousetablet = wareHouseService.findByTabletwarehouse_TabletID(sectiontablet);

                    if (sectiontablet == warehousetablet.getTabletwarehouse().getTabletID()) {

                        model.addAttribute("warehouse", warehouse);
                        model.addAttribute("add", true);
                        model.addAttribute("newfabricid", true);
                        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                        model.addAttribute("tabletList", tabletList);
                        model.addAttribute("medeviceList", medeviceList);
                        model.addAttribute("errors", true);

                        String sameError1 = "Данный товар уже на складе";
                        model.addAttribute("sameError", true);
                        model.addAttribute("sameError1", sameError1);
                        return "warehouse-edit";
                    }
                } catch (Exception ignored){
                }

            } else if (sectionmedevice != 0) {
                try {
                    Warehouse warehousemedevice = wareHouseService.findByMedevicewarehouse_MedeviceID(sectionmedevice);

                    if (sectionmedevice == warehousemedevice.getMedevicewarehouse().getMedeviceID()) {

                        model.addAttribute("warehouse", warehouse);
                        model.addAttribute("add", true);
                        model.addAttribute("newfabricid", true);
                        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                        model.addAttribute("tabletList", tabletList);
                        model.addAttribute("medeviceList", medeviceList);
                        model.addAttribute("errors", true);

                        String sameError1 = "Данный товар уже на складе";
                        model.addAttribute("sameError", true);
                        model.addAttribute("sameError1", sameError1);
                        return "warehouse-edit";
                    }
                } catch (Exception ignored){
                }
            }

            if (sectiontablet == 0 && sectionmedevice == 0) {
                String nullSection = "Пожалуйства выберите лекарственный препарат или медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);

                List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                model.addAttribute("add", true);
                model.addAttribute("newfabricid", true);
                model.addAttribute("warehouse", warehouse);
                model.addAttribute("tabletList", tabletList);
                model.addAttribute("medeviceList", medeviceList);
                model.addAttribute("errors", true);

                return "warehouse-edit";
            } else if (sectiontablet != 0 && sectionmedevice != 0) {
                String nullSection = "Пожалуйства выберите ТОЛЬКО ЛИБО лекарственный препарат ЛИБО медицинский прибор, который будет закупаться";
                model.addAttribute("nullSectionError", true);
                model.addAttribute("nullSection", nullSection);

                List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
                List<Medevice> medeviceList = medeviceService.getAllMedevieces();
                model.addAttribute("add", true);
                model.addAttribute("newfabricid", true);
                model.addAttribute("warehouse", warehouse);
                model.addAttribute("tabletList", tabletList);
                model.addAttribute("medeviceList", medeviceList);
                model.addAttribute("errors", true);

                return "warehouse-edit";
            } else if (sectiontablet != 0) {
                Tablet tablet = new Tablet();
                tablet.setTabletID(sectiontablet);
                warehouse.setTabletwarehouse(tablet);
            } else if (sectionmedevice != 0) {
                Medevice medevice = new Medevice();
                medevice.setMedeviceID(sectionmedevice);
                warehouse.setMedevicewarehouse(medevice);
            }

            wareHouseService.save(warehouse);
            return "redirect:/warehouse";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/warehouse/{warehouseID}/edit"})
    public String showEditWareHouse(Model model, @PathVariable int warehouseID) {
        Warehouse warehouse = null;
        try {
            warehouse = wareHouseService.findById(warehouseID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("add", false);
        model.addAttribute("warehouse", warehouse);
        return "warehouse-edit";
    }

    @PostMapping(value = {"/warehouse/{warehouseID}/edit"})
    public String updateWareHouse(
            @Valid @ModelAttribute("warehouse") Warehouse warehouse, BindingResult bindingResult, Model model,
            @PathVariable int warehouseID) {

        if (bindingResult.hasErrors()) {

            List<FieldError> errorsMaps = new ArrayList<>(bindingResult.getFieldErrors());

            model.addAttribute("errorsMaps", errorsMaps);

            model.addAttribute("add", false);
            model.addAttribute("warehouse", warehouse);
            model.addAttribute("errors", true);
            return "warehouse-edit";
        }

        try {

            warehouse.setWarehouseID(warehouseID);

            wareHouseService.update(warehouse);
            return "redirect:/warehouse";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/warehouse/{warehouseID}/delete"})
    public String deleteWareHouse(
            Model model, @PathVariable int warehouseID) {

        try {
            wareHouseService.deleteById(warehouseID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        return "redirect:/warehouse";
    }
}
