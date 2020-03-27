package com.sun.hospital.service;


import com.sun.hospital.entiy.User;

import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/13 - 20:26
 */
public interface UserService {
    //登入
    boolean login(User user);

    //是否注册
    boolean isregister(String username);

    //添加
    boolean register(User user);

    //
    User type(User user);

    List<User> fandAllUser();

    boolean deleteUser(int id);

    boolean updateUser(int id, String userType);

    User findById(int id);

    boolean updateUserByName(String userType, String username);

}
