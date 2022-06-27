package com.diplom.apteka.repository;

import com.diplom.apteka.model.Tablet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TabletRepository extends PagingAndSortingRepository<Tablet, Integer>,
        JpaSpecificationExecutor<Tablet> {

//    @Query("SELECT e FROM Tablet e  WHERE e.tabletTo.tablettoid = ?1")
    List<Tablet> findByTabletTo_Tablettoid (int tablettoid);

    List<Tablet> findByMaking (String making);

    Tablet findByTabletID (int tabletID);

//    List<Tablet> findById(long tablettoid);
}
