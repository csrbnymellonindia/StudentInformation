package com.bezkoder.spring.jpa.h2.service.model;

import java.util.List;

public class ListofStudent {
    List<Long> arr;
    String body;
    String subject;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Long> getArr() {
        return arr;
    }

    public void setArr(List<Long> arr) {
        this.arr = arr;
    }

    public ListofStudent(List<Long> arr, String body, String subject) {
        this.arr = arr;
        this.body = body;
        this.subject = subject;
    }

    public ListofStudent() {
    }

    @Override
    public String toString() {
        return "ListofStudent [arr=" + arr + "]";
    }
    
}
