package com.diplom.apteka.repository;

import com.diplom.apteka.model.Medevice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MedeviceRepository extends PagingAndSortingRepository<Medevice, Integer>,
        JpaSpecificationExecutor<Medevice> {
}
