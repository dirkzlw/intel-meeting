package com.intel.meeting.repository;

import com.intel.meeting.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ranger
 * @create 2019-09 -03-9:24
 */
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findDistinctByRoleName(String rolename);
}
