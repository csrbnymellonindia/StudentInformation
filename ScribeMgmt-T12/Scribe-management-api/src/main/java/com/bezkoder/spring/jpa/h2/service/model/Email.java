package com.bezkoder.spring.jpa.h2.service.model;

public class Email {
    String toEmail;
    String subject;
    String body;
    public String getToEmail() {
        return toEmail;
    }
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
    public Email() {
    }

    public Email(String toEmail, String subject, String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Email [toEmail=" + toEmail + ", subject=" + subject + ", body=" + body + "]";
    }
}
