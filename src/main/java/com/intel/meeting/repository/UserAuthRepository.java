package com.intel.meeting.repository;

import com.intel.meeting.po.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Intel-Meeting
 * @create 2019-09 -03-10:53
 */
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

    int countByAuthStatus(Integer authStatus);

}
