package com.diplom.apteka.web;

import com.diplom.apteka.model.Cart;
import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Purchase;
import com.diplom.apteka.model.Warehouse;
import com.diplom.apteka.service.CartService;
import com.diplom.apteka.service.EmployeeService;
import com.diplom.apteka.service.PurchaseService;
import com.diplom.apteka.service.WareHouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CartService cartService;

    @Autowired
    private WareHouseService wareHouseService;

    @Autowired
    private EmployeeService employeeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/purchase")
    public String getCarts(Model model) throws Exception {
        List<Purchase> purchaseList = purchaseService.getAllPurchase();

        model.addAttribute("purchaseList", purchaseList);

        return "purchase-list";
    }

    @RequestMapping(value = "/purchase/add")
    public String getPurcahse(@CurrentSecurityContext(expression="authentication?.name") String username, Model model
//                          , @ModelAttribute("cartList") ArrayList<Cart> cartList
            , @RequestParam(name = "cartID", required = true) List<Integer> cartID
    ) throws Exception {

//        if (cartID.isEmpty()){
//            return "cart-list";
//        }

        Purchase purchase = new Purchase();
        int sum = 0;
        StringBuilder names = new StringBuilder();

        for (Integer integer : cartID) {

            Cart cart = cartService.findByCartID(integer);
            Warehouse warehouse = wareHouseService.findByWarehouseID(cart.getWarehousecart().getWarehouseID());

            warehouse.setStockavailability(warehouse.getStockavailability() - cart.getAmount());
            wareHouseService.update(warehouse);

            cart.setIsactive(0);

            try {
                names.append(cart.getWarehousecart().getTabletwarehouse().getTitle());
            } catch (Exception ignored) { }

            try {
                names.append(cart.getWarehousecart().getMedevicewarehouse().getTitle());
            } catch (Exception ignored) { }

            names.append(", ");
            sum = sum + (cart.getAmount() * cart.getWarehousecart().getPrice());


            cartService.deleteById(integer);
        }

        names.append(".");
        purchase.setProduct(names.toString());
        purchase.setSum(sum);

        int minDiscount = 1;
        int maxDiscount = 15;
        double x = (Math.random() * ((maxDiscount - minDiscount) + 1)) + minDiscount;
        int i = (int) x;

        purchase.setDiscount(i);

        String timeStamp = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        purchase.setDatepurchase(timeStamp);

        try {
            Employee employee = employeeService.findByUsername(username);
            purchase.setEmployee(employee);
        } catch (Exception ignored){}

        purchaseService.save(purchase);


//        Employee employee = employeeService.findByUsername(username);
//
//        List<Cart> cartList = cartService.findByIdtransactionAndIsactive(employee.getEmployeeID(), 1);

//        List<Cart> cartList = cartService.getAllCart();

//        model.addAttribute("purchase", purchase);

        return "redirect:/purchase";
    }

    @GetMapping(value = {"/purchase/{purchaseID}/delete"})
    public String deletePurchase(
            Model model, @PathVariable int purchaseID) {
        try {
            purchaseService.deleteById(purchaseID);
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            return "error-page";
        }
        model.addAttribute("allowDelete", true);
        return "redirect:/purchase";
    }
}
