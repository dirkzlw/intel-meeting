package com.intel.meeting.repository;

import com.intel.meeting.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findDistinctByUsernameOrEmail(String username,String email);

}
