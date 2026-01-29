package com.iamcarlchen.crm.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Small helper for generating BCrypt hashes in dev. */
public class PasswordUtil {
  public static void main(String[] args) {
    var enc = new BCryptPasswordEncoder();
    System.out.println(enc.encode(args.length > 0 ? args[0] : "admin123"));
  }
}
