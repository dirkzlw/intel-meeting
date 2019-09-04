package com.intel.meeting.service.impl;

import com.intel.meeting.po.Role;
import com.intel.meeting.po.User;
import com.intel.meeting.repository.RoleRepository;
import com.intel.meeting.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-9:36
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByRoleName(String roleName){
        Role role = roleRepository.findDistinctByRoleName(roleName);
        System.out.println("roleName = " + roleName);
        return role;
    }
}
