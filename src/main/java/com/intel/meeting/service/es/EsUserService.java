package com.intel.meeting.service.es;

import com.intel.meeting.po.es.EsMeetingRoom;
import com.intel.meeting.po.es.EsUser;

import java.util.List;
public interface EsUserService {
    List<EsUser> findEsUserByUsername(String username);
    void save(EsUser esUser);
    void delEsUserById(Integer userId);
    EsUser findEsUserById(Integer userId);
}
