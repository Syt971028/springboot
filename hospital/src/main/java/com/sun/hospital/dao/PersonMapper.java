package com.sun.hospital.dao;

import com.sun.hospital.entiy.Personal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/27 - 12:53
 */
@Mapper
public interface PersonMapper {

    @Insert("insert into personal(pname,psex,department,phone,username) values (#{pname},#{psex},#{department},#{phone},#{username})")
    int savePerson(Personal personal);

    @Select("select * from personal where username in (select username from user where usertype='doctor')")
    List<Personal> findDoctorPerson();
}
