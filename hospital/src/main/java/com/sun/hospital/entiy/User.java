package com.sun.hospital.entiy;

import java.io.Serializable;

/**
 * @author 孙耘田
 * @date 2020/3/25 - 23:18
 */
public class User implements Serializable {
    private static final long serialVersionUID = 194354501302468298L;

    private Integer id;

    private String username;

    private String password;

    private String userType;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
