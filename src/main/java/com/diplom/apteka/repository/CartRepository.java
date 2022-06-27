package com.diplom.apteka.repository;

import com.diplom.apteka.model.Cart;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CartRepository extends PagingAndSortingRepository<Cart, Integer>,
        JpaSpecificationExecutor<Cart> {

    List<Cart> findByIdtransactionAndIsactive(Long idtransaction, Integer isactive);

    Cart findByCartID (Integer cartID);

//    Cart findByWarehousecartWarehouseIDaAndIsactive(Integer id, Integer isactive);

    boolean findByWarehousecartWarehouseIDAndIsactive(Integer id, Integer isactive);

    boolean existsByWarehousecartWarehouseIDAndIsactive (Integer id, Integer isactive);

}
