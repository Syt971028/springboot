package com.sun.hospital.service.impl;

import com.sun.hospital.dao.RelationMapper;
import com.sun.hospital.entiy.Personal;
import com.sun.hospital.service.RelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/28 - 20:07
 */
@Service
public class RelationServiceImpl implements RelationService {
    @Resource
    private RelationMapper relationMapper;

    @Override
    public List<Personal> findPatient(String username) {
        List<Personal> list = relationMapper.findPatient(username);
        return list;
    }
}
