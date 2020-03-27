package com.sun.hospital.service.impl;

import com.sun.hospital.dao.UserMapper;
import com.sun.hospital.entiy.User;
import com.sun.hospital.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙耘田
 * @date 2020/3/13 - 20:27
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    //登入:账号密码正确返回true
    @Override
    public boolean login(User user) {
        User byNameAndPwd = userMapper.findByNameAndPwd(user);
        return byNameAndPwd == null ? false : true;
    }

    //是否注册：没注册返回true
    @Override
    public boolean isregister(String username) {
        String byName = userMapper.findByName(username);
        return byName == null ? true : false;
    }

    //添加用户成功返回true
    @Override
    public boolean register(User user) {
        int rows = userMapper.saveUser(user);
        return rows == 1 ? true : false;
    }

    @Override
    public User type(User user) {
        User byNameAndPwd = userMapper.findByNameAndPwd(user);
        return byNameAndPwd;
    }

    @Override
    public List<User> fandAllUser() {
        List<User> all = userMapper.findAll();
        return all;
    }

    @Override
    public boolean deleteUser(int id) {
        int i = userMapper.deleteUser(id);
        return i == 1 ? true : false;
    }

    @Override
    public boolean updateUser(int id, String userType) {
        int i = userMapper.updateUser(userType, id);
        return i == 1 ? true : false;
    }

    @Override
    public User findById(int id) {
        User users = userMapper.findById(id);
        return users;
    }

    @Override
    public boolean updateUserByName(String userType, String username) {
        int i = userMapper.updateUserByName(userType, username);
        return i == 1 ? true : false;
    }
}
