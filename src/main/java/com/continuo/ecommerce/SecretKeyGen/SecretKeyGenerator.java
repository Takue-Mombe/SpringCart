package com.continuo.ecommerce.SecretKeyGen;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        String encodeKey= Base64.getEncoder().encodeToString(key);

        System.out.println("Generated Key: " + encodeKey);
    }
}
