package com.diplom.apteka.service;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Fabrications;
import com.diplom.apteka.model.Medevice;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.repository.FabricationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FabricationsService {

    @Autowired
    FabricationsRepository fabricationsRepository;

    @Autowired


    public List<Fabrications> getAllFabrications(){
        List<Fabrications> fabricationsList = new ArrayList<>();
        fabricationsRepository.findAll().forEach(fabricationsList::add);
        return fabricationsList;
    }

//    public List<Fabrications> findByIdTablet(int tablettoid) {
//        return fabricationsRepository.findByTabletTo_Tablettoid(tablettoid);
//    }

    private boolean existsById(int id) {
        return fabricationsRepository.existsById(id);
    }

    public Fabrications findById(int employeeID) {
        return fabricationsRepository.findById(employeeID).orElse(null);
    }

    public Fabrications save(Fabrications fabrications) throws Exception {
        return fabricationsRepository.save(fabrications);
    }

    public void update(Fabrications fabrications) throws Exception {
        fabricationsRepository.save(fabrications);
    }

    public Fabrications deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Fabrication Order with id: " + id);
        }
        else {
            fabricationsRepository.deleteById(id);
        }
        return null;
    }

    public Long count() {
        return fabricationsRepository.count();
    }
}
