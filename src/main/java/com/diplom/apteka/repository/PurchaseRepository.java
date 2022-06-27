package com.diplom.apteka.repository;

import com.diplom.apteka.model.Purchase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Integer>,
        JpaSpecificationExecutor<Purchase> {
}
