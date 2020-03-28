package com.sun.hospital.service;

import com.sun.hospital.entiy.Personal;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/28 - 20:06
 */
public interface RelationService {

    List<Personal> findPatient(String username);
}
