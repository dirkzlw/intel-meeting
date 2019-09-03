package com.intel.meeting.service;

import com.intel.meeting.po.Role;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-9:29
 */
public interface RoleService {
    List<Role> findAllRoles();
}
