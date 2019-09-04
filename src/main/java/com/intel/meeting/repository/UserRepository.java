package com.intel.meeting.repository;

import com.intel.meeting.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ranger
 * @create 2019-09 -03-13:51
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findDistinctByUsernameOrEmail(String username,String email);
    User findByEmail(String email);
    User findByUsername(String username);

}
