package com.arana.guitar.notebook.practice.domain.util;
import com.arana.guitar.notebook.practice.domain.objects.PasswordHash;
import jakarta.validation.constraints.NotBlank;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Sha256PasswordService implements PasswordService {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 100_000;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public PasswordHash hash(String rawPassword) {
        byte[] salt = generateSalt();
        byte[] hash = hash(rawPassword, salt, ITERATIONS);

        return new PasswordHash(
                Base64.getEncoder().encodeToString(hash),
                Base64.getEncoder().encodeToString(salt),
                ITERATIONS
        );
    }

    @Override
    public boolean matches(@NotBlank String rawPassword, PasswordHash storedHash) {
        byte[] salt = Base64.getDecoder().decode(storedHash.salt());
        byte[] expectedHash = Base64.getDecoder().decode(storedHash.hash());

        byte[] actualHash = hash(rawPassword, salt, storedHash.iterations());

        return MessageDigest.isEqual(actualHash, expectedHash);
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private byte[] hash(String password, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] input = concatenate(
                    password.getBytes(StandardCharsets.UTF_8),
                    salt
            );

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                result = digest.digest(result);
            }

            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Password hashing failed", e);
        }
    }

    private byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}