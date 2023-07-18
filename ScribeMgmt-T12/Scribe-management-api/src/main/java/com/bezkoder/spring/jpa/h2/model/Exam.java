package com.bezkoder.spring.jpa.h2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="desc")
    private String desc;

    @Column(name="address")
    private String address;

    @Column(name="date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "IST")
    private Date date;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="postalcode")
    private int postalcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Exam [id=" + id + ", name=" + name + ", desc=" + desc + ", address=" + address + ", date=" + date + "]";
    }

    public Exam() {
        //
    }

    public Exam(String name, String desc, String address, Date date, String city, String state, int postalcode) {
        this.name = name;
        this.desc = desc;
        this.address = address;
        this.date = date;
        this.city = city;
        this.state = state;
        this.postalcode = postalcode;
    }

    

}
