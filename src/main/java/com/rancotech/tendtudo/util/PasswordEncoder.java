package com.rancotech.tendtudo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    int x;
    String nome;
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("maria"));

        byte a = 126;
        byte b = 0;
        a += b;
    PasswordEncoder pe = new PasswordEncoder();
        System.out.println(a);
    }

}
