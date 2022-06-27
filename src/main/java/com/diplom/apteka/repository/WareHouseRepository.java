package com.diplom.apteka.repository;

import com.diplom.apteka.model.Warehouse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WareHouseRepository extends PagingAndSortingRepository<Warehouse, Integer>,
        JpaSpecificationExecutor<Warehouse> {

    Warehouse findByWarehouseID (Integer id);

    Warehouse findByTabletwarehouseTitle (String title);

    Warehouse findByMedevicewarehouseTitle (String title);

    Warehouse findByTabletwarehouse_TabletID (Integer id);

    Warehouse findByMedevicewarehouse_MedeviceID (Integer id);
}
