package com.diplom.apteka.service;

import com.diplom.apteka.model.Buying;
import com.diplom.apteka.model.Fabrications;
import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.repository.BuyingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyingService {

    @Autowired
    BuyingRepository buyingRepository;

    public List<Buying> getAllBuying(){
        List<Buying> buyingsList = new ArrayList<>();
        buyingRepository.findAll().forEach(buyingsList::add);
        return buyingsList;
    }

    private boolean existsById(int id) {
        return buyingRepository.existsById(id);
    }

    public Buying findById(int employeeID) {
        return buyingRepository.findById(employeeID).orElse(null);
    }

    public Buying save(Buying buying) throws Exception {
        return buyingRepository.save(buying);
    }

    public void update(Buying buying) throws Exception {
        buyingRepository.save(buying);
    }

    public Buying deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Buying Order with id: " + id);
        }
        else {
            buyingRepository.deleteById(id);
        }
        return null;
    }

    public Long count() {
        return buyingRepository.count();
    }
}
