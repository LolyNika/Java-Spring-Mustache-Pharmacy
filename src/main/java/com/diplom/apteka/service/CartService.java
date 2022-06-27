package com.diplom.apteka.service;

import com.diplom.apteka.model.Cart;
import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Warehouse;
import com.diplom.apteka.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

//    public List<Cart> getAllCart(){
//        List<Cart> cartList = new ArrayList<>();
//        cartRepository.findAll().forEach(cartList::add);
//        return cartList;
//    }

    public Cart save(Cart cart) throws Exception {
        return cartRepository.save(cart);
    }

    public List<Cart> findByIdtransactionAndIsactive (Long idtransaction, int isactive) {
        return cartRepository.findByIdtransactionAndIsactive(idtransaction, isactive);
    }

    public boolean existsByWarehousecartWarehouseIDAndIsactive (int id, int isactive) {
        return cartRepository.existsByWarehousecartWarehouseIDAndIsactive(id, isactive);
    }

    public Cart findByCartID (Integer cartID){
        return cartRepository.findByCartID(cartID);
    }

    public Cart findById(int ID) {
        return cartRepository.findById(ID).orElse(null);
    }

    private boolean existsById(int id) {
        return cartRepository.existsById(id);
    }

    public Cart deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Cart with id: " + id);
        }
        else {
            cartRepository.deleteById(id);
        }
        return null;
    }
}
