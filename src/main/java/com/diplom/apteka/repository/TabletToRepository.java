package com.diplom.apteka.repository;

import com.diplom.apteka.model.Tabletto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TabletToRepository extends PagingAndSortingRepository<Tabletto, Integer>,
        JpaSpecificationExecutor<Tabletto> {

    Tabletto findByTablettoid (int id);
}
