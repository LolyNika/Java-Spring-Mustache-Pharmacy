package com.diplom.apteka.repository;

import com.diplom.apteka.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>,
        JpaSpecificationExecutor<Role> {
}
