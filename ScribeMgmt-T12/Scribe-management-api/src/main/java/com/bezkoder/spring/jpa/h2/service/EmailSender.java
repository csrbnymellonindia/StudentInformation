package com.bezkoder.spring.jpa.h2.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("r.navaneeth992@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        
        mailSender.send(message);
    }

    public void sendEmailHTMLToVoluntter(String toEmail, String subject, Date date, String link) throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "            color: white !important;\n" +
                "            font-weight: bold;\n"+
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #2f42ed;\n" +
                "            border-radius: 4px;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Invitation to Volunteer</h1>\n" +
                "        <p>You have been invited to volunteer for an exam on <strong>" + date + "</strong>. Please login to the website to accept or decline the invitation.</p>\n" +
                "        <p>\n" +
                "            To respond to the invitation, click the button below:\n" +
                "            <br/><br/>\n" +
                "            <a class=\"button\" href=\"" + link + "\">Respond to Invitation</a>\n" +
                "        </p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendEmailHTMLAdminToParent(String toEmail, String subject, String message, String contact, String email) throws MessagingException
    {
        MimeMessage message1 = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message1, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .signature {\n" +
                "            margin-top: 20px;\n" +
                "            border-top: 1px solid #ccc;\n" +
                "            padding-top: 10px;\n" +
                "            font-size: 14px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "        .contact-info {\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .contact-info span {\n" +
                "            display: inline-block;\n" +
                "            margin-right: 10px;\n" +
                "            color: #007bff;\n" +
                "        }\n" +
                "        a {\n" +
                "            color: #007bff;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007bff;\n" +
                "            color: #fff;\n" +
                "            border-radius: 4px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Message from Admin</h1>\n" +
                "        <p>" + message + "</p>\n" +
                "        <p>Thank You</p>\n" +
                "        <div class=\"signature\">\n" +
                "            <p>Contact:</p>\n" +
                "            <div class=\"contact-info\">\n" +
                "                <span>Email: " + email + "</span><br>\n" +
                "                <span>Phone No: " + contact + "</span><br>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        helper.setText(htmlContent, true);

        mailSender.send(message1);
    }

    public void sendEmailHTMLVolunteerToParent(String toEmail, String subject, String message, String contact, String email, String name) throws MessagingException
    {
        MimeMessage message1 = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message1, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .signature {\n" +
                "            margin-top: 20px;\n" +
                "            border-top: 1px solid #ccc;\n" +
                "            padding-top: 10px;\n" +
                "            font-size: 14px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "        .contact-info {\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .contact-info span {\n" +
                "            display: inline-block;\n" +
                "            margin-right: 10px;\n" +
                "            color: #007bff;\n" +
                "        }\n" +
                "        a {\n" +
                "            color: #007bff;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007bff;\n" +
                "            color: #fff;\n" +
                "            border-radius: 4px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Message from Volunteer</h1>\n" +
                "        <p>" + message + "</p>\n" +
                "        <p>Thank You</p>\n" +
                "        <div class=\"signature\">\n" +
                "            <p>Contact:</p>\n" +
                "            <div class=\"contact-info\">\n" +
                "                <span>Email: " + email + "</span><br>\n" +
                "                <span>Phone No: " + contact + "</span><br>\n" +
                "                <span>Name: " + name + "</span><br>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        helper.setText(htmlContent, true);

        mailSender.send(message1);
    }

    public void sendEmailHTMLAutomatically(String toEmail, String subject, Date date) throws MessagingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007bff;\n" +
                "            color: #fff;\n" +
                "            border-radius: 4px;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Reminder for Exam</h1>\n" +
                "        <p>This is a reminder that you have an exam on <strong>" + date + "</strong>. Please volunteer for the test without fail.</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
