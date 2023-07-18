package com.bezkoder.spring.jpa.h2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class HashPasswordTest {
    @Test
    public void hashedPasswordTest(){
        String hashedPwd = HashPassword.hashedPassword("password");
        assertTrue(HashPassword.checkPassword("password", hashedPwd));
    }

}
