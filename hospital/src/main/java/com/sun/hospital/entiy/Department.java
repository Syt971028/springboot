package com.sun.hospital.entiy;

import java.io.Serializable;

/**
 * (Department)实体类
 *
 * @author makejava
 * @since 2020-03-26 23:49:00
 */
public class Department implements Serializable {
    private static final long serialVersionUID = -97105035126757039L;

    private Integer id;

    private String departname;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departname='" + departname + '\'' +
                '}';
    }
}