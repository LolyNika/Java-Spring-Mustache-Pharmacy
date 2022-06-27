package com.diplom.apteka.repository;

import com.diplom.apteka.model.Buying;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BuyingRepository extends PagingAndSortingRepository<Buying, Integer>,
        JpaSpecificationExecutor<Buying> {

}
