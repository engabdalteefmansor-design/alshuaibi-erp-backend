package com.alshuaibi.erp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TempPasswordGenerator {
    public static void main(String[] args) {
        String raw = "admin123";
        String hash = "$2a$10$WqjA0Kz4J4V6B7D3Vw8n0eB5nP8Q5RrK5QmYjV5m0Y9m6jM1KxV2K";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("RAW: " + raw);
        System.out.println("HASH: " + hash);
        System.out.println("MATCHES: " + encoder.matches(raw, hash));
        System.out.println("NEW_HASH_FOR_admin123: " + encoder.encode(raw));
    }
}