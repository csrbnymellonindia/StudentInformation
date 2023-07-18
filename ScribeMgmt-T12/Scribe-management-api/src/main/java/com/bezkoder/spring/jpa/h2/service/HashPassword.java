package com.bezkoder.spring.jpa.h2.service;

import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {
    private static int workload = 12;

    public static String hashedPassword(String password)
    {
        
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hashedPassword)
    {
        return BCrypt.checkpw(password,hashedPassword);
    }
}
