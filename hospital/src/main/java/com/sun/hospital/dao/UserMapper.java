package com.sun.hospital.dao;

import com.sun.hospital.entiy.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/13 - 20:23
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    User findByNameAndPwd(User user);

    @Select("select * from user where username=#{username}")
    String findByName(String username);

    @Insert("insert into user(username,password) values(#{username},#{password})")
    int saveUser(User user);

    @Select("select * from user")
    List<User> findAll();

    @Update("update user set usertype=#{userType} where id=#{id}")
    int updateUser(String userType, int id);

    @Update("update user set usertype=#{userType} where username=#{username}")
    int updateUserByName(String userType, String username);

    @Delete("delete from user where id=#{id}")
    int deleteUser(int id);

    @Select("select * from user where id=#{id}")
    User findById(int id);

}
