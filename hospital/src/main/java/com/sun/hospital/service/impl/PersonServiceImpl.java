package com.sun.hospital.service.impl;

import com.sun.hospital.dao.PersonMapper;
import com.sun.hospital.entiy.Personal;
import com.sun.hospital.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:55
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Resource
    private PersonMapper personMapper;

    @Override
    public boolean savePerson(Personal personal) {
        int i = personMapper.savePerson(personal);
        return i == 1 ? true : false;
    }

    @Override
    public List<Personal> findDoctorPerson() {
        List<Personal> list = personMapper.findDoctorPerson();
        return list;
    }
}
