package com.diplom.apteka.service;

import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.repository.MedeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedeviceService {

    @Autowired
    MedeviceRepository medeviceRepository;

    public List<Medevice> getAllMedevieces(){
        List<Medevice> medevicesList = new ArrayList<>();
        medeviceRepository.findAll().forEach(medevicesList::add);
        return medevicesList;
    }

    public Medevice save(Medevice medevice) throws Exception {
        return medeviceRepository.save(medevice);
    }

    public void update(Medevice medevice) throws Exception {
        medeviceRepository.save(medevice);
    }

    public Medevice findById(int ID) {
        return medeviceRepository.findById(ID).orElse(null);
    }

    private boolean existsById(int id) {
        return medeviceRepository.existsById(id);
    }

    public Medevice deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Medevice with id: " + id);
        }
        else {
            medeviceRepository.deleteById(id);
        }
        return null;
    }

}
