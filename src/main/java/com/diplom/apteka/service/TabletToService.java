package com.diplom.apteka.service;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Fabrications;
import com.diplom.apteka.model.Tabletto;
import com.diplom.apteka.repository.TabletToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TabletToService {

    @Autowired
    TabletToRepository tabletToRepository;

    public List<Tabletto> getAllTabletTo(){
        List<Tabletto> tablettoList = new ArrayList<>();
        tabletToRepository.findAll().forEach(tablettoList::add);
        return tablettoList;
    }

    public Tabletto findById(int id){
        return tabletToRepository.findByTablettoid(id);
    }
}
