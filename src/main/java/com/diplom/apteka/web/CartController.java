package com.diplom.apteka.web;

import com.diplom.apteka.model.Cart;
import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Warehouse;
import com.diplom.apteka.service.CartService;
import com.diplom.apteka.service.EmployeeService;
import com.diplom.apteka.service.WareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WareHouseService wareHouseService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/cart/tobuy")
    public String getWareHouse(Model model) {

        List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

        model.addAttribute("wareHousesList", wareHousesList);

        return "tobuy-list";
    }

    @RequestMapping(value = "/cart")
    public String getCart(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) throws Exception {
        Employee employee = employeeService.findByUsername(username);

        List<Cart> cartList12 = cartService.findByIdtransactionAndIsactive(employee.getEmployeeID(), 1);

        if (cartList12.isEmpty()){
            String emptyCart = "Корзина пуста!";

            model.addAttribute("emptyCart", emptyCart);
            model.addAttribute("errorCart", true);
            model.addAttribute("add", false);
            model.addAttribute("errors", true);

            List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

            model.addAttribute("wareHousesList", wareHousesList);
            return "tobuy-list";
        }

        model.addAttribute("cartList12", cartList12);

        return "cart-list";
    }


//    @GetMapping(value = {"/cart/add"})
//    public String showAddEmployee(Model model) {
//        Warehouse warehouse = new Warehouse();
//        List<Tablet> tabletList = tabletService.getAllTabletsNoPages();
//        List<Medevice> medeviceList = medeviceService.getAllMedevieces();
//        model.addAttribute("add", true);
//        model.addAttribute("newfabricid", true);
//        model.addAttribute("warehouse", warehouse);
//        model.addAttribute("tabletList", tabletList);
//        model.addAttribute("medeviceList", medeviceList);
//        return "warehouse-edit";
//    }

    @PostMapping(value = "/cart/add")
    public String addCart(@RequestParam(name = "amount", required = true) int amount, Model model,
                          @CurrentSecurityContext(expression = "authentication?.name") String username,
//                          @ModelAttribute("buying") Warehouse warehouse,
                          @RequestParam(name = "warehouseID", required = true) int warehouseID) {

        if (amount <= 0) {
            model.addAttribute("errorsMaps", true);

            String defaultMessage = "Введите в поле Количество значение 1-100";

            model.addAttribute("defaultMessage", defaultMessage);
            model.addAttribute("add", false);
            model.addAttribute("errors", true);

            List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

            model.addAttribute("wareHousesList", wareHousesList);
            return "tobuy-list";
        }

        try {

            Warehouse warehouseempty = wareHouseService.findByWarehouseID(warehouseID);
            if (warehouseempty.getStockavailability() < amount){
                model.addAttribute("errorsMaps", true);

                String defaultMessage = "Недостаточное количество данного товара";

                model.addAttribute("defaultMessage", defaultMessage);
                model.addAttribute("add", false);
                model.addAttribute("errors", true);

                List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

                model.addAttribute("wareHousesList", wareHousesList);
                return "tobuy-list";
            }


            Employee employee = employeeService.findByUsername(username);
//            int idtransaction = Math.toIntExact(employee.getEmployeeID());

//            for(Integer elementAmount : amount) {
//                Cart cart = new Cart();
//                cart.setAmount(elementAmount);
//
//                for(Integer elementID : warehouseId) {
//
//                    Warehouse warehouse = new Warehouse();
//                    warehouse.setWarehouseID(elementID);
//                    cart.setWarehousecart(warehouse);
//                    break;
//                }
//                cartService.save(cart);
//            }

            try {
                boolean cart = cartService.existsByWarehousecartWarehouseIDAndIsactive(warehouseID, 1);
                if (cart) {
                String alreadyCreated = "Данный товар уже находится в корзине!";
                model.addAttribute("alreadyCreated", alreadyCreated);
                model.addAttribute("createdError", true);
                model.addAttribute("errors", true);

                    List<Warehouse> wareHousesList = wareHouseService.getAllWareHouse();

                    model.addAttribute("wareHousesList", wareHousesList);
                return "tobuy-list";
                }
            }catch (Exception ignored){ }

            Cart cart = new Cart();
            cart.setAmount(amount);
            cart.setIsactive(1);
            cart.setIdtransaction(employee.getEmployeeID());
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseID(warehouseID);
            cart.setWarehousecart(warehouse);
            cartService.save(cart);

            return "redirect:/cart/tobuy";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
    }

    @GetMapping(value = {"/cart/{cartID}/delete"})
    public String deleteCart(
            Model model, @PathVariable int cartID) {
        try {
            cartService.deleteById(cartID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/cart";
    }
}
