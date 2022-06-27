package com.diplom.apteka.repository;

import com.diplom.apteka.model.Fabrications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FabricationsRepository extends PagingAndSortingRepository<Fabrications, Integer>,
        JpaSpecificationExecutor<Fabrications> {
}
