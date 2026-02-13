package com.tikam.simple_admin_v2.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
public class PasswordUtils {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALGORITHM = "SHA-256";

    // Generate a random 16-byte salt
    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash the password with the salt
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            // Add salt to the digest
            md.update(Base64.getDecoder().decode(salt));
            // Hash the password
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            log.info("Hashed Password: {}", Base64.getEncoder().encodeToString(hashedPassword));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error hashing password", e);
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify if the provided password matches the stored hash
    public static boolean verifyPassword(String providedPassword, String storedSalt, String storedHash) {
        String newHash = hashPassword(providedPassword, storedSalt);
        return newHash.equals(storedHash);
    }
    
    // For testing purposes
    public static void main(String[] args) {
        String salt = "salt123";
        String password = "password123";
        String hash = hashPassword(password, salt);
        
        System.out.println("Salt: " + salt);
        System.out.println("Hash: " + hash);
        System.out.println("Verify: " + verifyPassword("password123", salt, hash));

        Map<Integer,Integer> mp = new HashMap();
        List<Integer> ans = Arrays.asList(1,2,3);
    }
}
