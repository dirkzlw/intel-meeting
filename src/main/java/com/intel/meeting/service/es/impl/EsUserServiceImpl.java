package com.intel.meeting.service.es.impl;

import com.intel.meeting.po.es.EsUser;
import com.intel.meeting.repository.es.EsMeetingRoomRepository;
import com.intel.meeting.repository.es.EsUserRepository;
import com.intel.meeting.service.es.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsUserServiceImpl implements EsUserService {
    @Autowired
    private EsUserRepository esUserRepository;
    @Override
    public List<EsUser> findEsUserByUsername(String username) {

        return esUserRepository.findByUsernameContaining(username);

    }

    @Override
    public void save(EsUser esUser) {
        esUserRepository.save(esUser);
    }

    @Override
    public void delEsUserById(Integer userId) {
        esUserRepository.delete(userId);

    }

    @Override
    public EsUser findEsUserById(Integer userId) {
        return esUserRepository.findOne(userId);
    }
}
