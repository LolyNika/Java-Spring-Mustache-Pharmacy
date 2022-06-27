package com.diplom.apteka.service;

import com.diplom.apteka.model.Cart;
import com.diplom.apteka.model.Fabrications;
import com.diplom.apteka.model.Warehouse;
import com.diplom.apteka.repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    public List<Warehouse> getAllWareHouse(){
        List<Warehouse> warehouseList = new ArrayList<>();
        wareHouseRepository.findAll().forEach(warehouseList::add);
        return warehouseList;
    }

    public Warehouse findByTabletwarehouseTitle (String title) {
        return wareHouseRepository.findByTabletwarehouseTitle(title);
    }

    public Warehouse findByMedevicewarehouseTitle (String title){
        return wareHouseRepository.findByMedevicewarehouseTitle(title);
    }

    public Warehouse findByTabletwarehouse_TabletID (int id) {
        return wareHouseRepository.findByTabletwarehouse_TabletID(id);
    }

    public Warehouse findByMedevicewarehouse_MedeviceID (int id){
        return wareHouseRepository.findByMedevicewarehouse_MedeviceID(id);
    }

    public Warehouse save(Warehouse warehouse) throws Exception {
        return wareHouseRepository.save(warehouse);
    }

    private boolean existsById(int id) {
        return wareHouseRepository.existsById(id);
    }

    public Warehouse findById(int employeeID) {
        return wareHouseRepository.findById(employeeID).orElse(null);
    }

    public void update(Warehouse warehouse) throws Exception {
        wareHouseRepository.save(warehouse);
    }

    public Warehouse deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Warehouse Order with id: " + id);
        }
        else {
            wareHouseRepository.deleteById(id);
        }
        return null;
    }

    public Warehouse findByWarehouseID (Integer ID){
        return wareHouseRepository.findByWarehouseID(ID);
    }
}
