package com.intel.meeting.service;

import com.intel.meeting.po.Role;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-9:29
 */
public interface RoleService {
    List<Role> findAllRoles();
    Role findByRoleName(String roleName);
}
