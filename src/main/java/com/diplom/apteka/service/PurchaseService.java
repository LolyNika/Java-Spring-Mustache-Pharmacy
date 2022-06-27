package com.diplom.apteka.service;

import com.diplom.apteka.model.Cart;
import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Purchase;
import com.diplom.apteka.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchase(){
        List<Purchase> purchaseList = new ArrayList<>();
        purchaseRepository.findAll().forEach(purchaseList::add);
        return purchaseList;
    }

    public Purchase save(Purchase purchase) throws Exception {
        return purchaseRepository.save(purchase);
    }

    public Purchase findById(int ID) {
        return purchaseRepository.findById(ID).orElse(null);
    }

    private boolean existsById(int id) {
        return purchaseRepository.existsById(id);
    }

    public Purchase deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Purchase with id: " + id);
        }
        else {
            purchaseRepository.deleteById(id);
        }
        return null;
    }
}
