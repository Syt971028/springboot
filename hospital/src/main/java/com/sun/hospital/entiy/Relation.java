package com.sun.hospital.entiy;

import java.io.Serializable;

/**
 * (Relation)实体类
 *
 * @author makejava
 * @since 2020-03-28 19:52:19
 */
public class Relation implements Serializable {
    private static final long serialVersionUID = 817554520157865555L;
    
    private String doctor;
    
    private String patient;
    
    private Integer id;

    public Relation() {
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Relation{" +
                "doctor='" + doctor + '\'' +
                ", patient='" + patient + '\'' +
                ", id=" + id +
                '}';
    }
}