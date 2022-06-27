package com.diplom.apteka.service;

import com.diplom.apteka.model.Employee;
import com.diplom.apteka.model.Role;
import com.diplom.apteka.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findById(Long roleID) {
        return roleRepository.findById(roleID).orElse(null);
    }
}
