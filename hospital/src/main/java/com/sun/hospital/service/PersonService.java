package com.sun.hospital.service;

import com.sun.hospital.entiy.Personal;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:55
 */
public interface PersonService {

    boolean savePerson(Personal personal);

    List<Personal> findDoctorPerson();
}
