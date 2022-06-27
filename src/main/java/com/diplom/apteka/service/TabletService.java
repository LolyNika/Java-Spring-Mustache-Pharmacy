package com.diplom.apteka.service;

import com.diplom.apteka.model.Buying;
import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Tablet;
import com.diplom.apteka.repository.TabletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TabletService {

    @Autowired
    TabletRepository tabletRepository;

    public List<Tablet> getAllTablets(int pageNumber, int rowPerPage){
        List<Tablet> tabletsList = new ArrayList<>();
        Pageable sortedByLastUpdateDesc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("tabletID").ascending());
        tabletRepository.findAll(sortedByLastUpdateDesc).forEach(tabletsList::add);
        return tabletsList;
    }

    public List<Tablet> getAllTabletsNoPages(){
        List<Tablet> tabletsList = new ArrayList<>();
        tabletRepository.findAll().forEach(tabletsList::add);
        return tabletsList;
    }

    public List<Tablet> findByIdTablet(int tablettoid) {
        return tabletRepository.findByTabletTo_Tablettoid(tablettoid);
    }

    public Tablet save(Tablet tablet) throws Exception {
        return tabletRepository.save(tablet);
    }

    public void update(Tablet tablet) throws Exception {
        tabletRepository.save(tablet);
    }

    public List<Tablet> findByMaking(String making) {
        return tabletRepository.findByMaking(making);
    }

    public Tablet findById(int ID) {
        return tabletRepository.findById(ID).orElse(null);
    }

    public Long count() {
        return tabletRepository.count();
    }

    private boolean existsById(int id) {
        return tabletRepository.existsById(id);
    }

    public Tablet deleteById(int id) throws Exception {
        if (!existsById(id)) {
            throw new Exception("Cannot find Tablet with id: " + id);
        }
        else {
            tabletRepository.deleteById(id);
        }
        return null;
    }
}
