package com.sun.hospital.dao;

import com.sun.hospital.entiy.Personal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/28 - 19:53
 */
@Mapper
public interface RelationMapper {

    @Select("select * from personal where username in (select patient from relation where doctor=#{username})")
    List<Personal> findPatient(String username);
}
