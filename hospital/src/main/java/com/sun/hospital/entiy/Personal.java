package com.sun.hospital.entiy;

import java.io.Serializable;

/**
 * (Personal)实体类
 *
 * @author makejava
 * @since 2020-03-27 12:44:51
 */
public class Personal implements Serializable {
    private static final long serialVersionUID = 652170774214972055L;

    private Integer id;

    private String pname;

    private String psex;

    private String department;

    private String phone;

    private String username;

    private User user;

    public User getUser() {
        return user;
    }

    public Personal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Object getPsex() {
        return psex;
    }

    public void setPsex(String psex) {
        this.psex = psex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", psex='" + psex + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}