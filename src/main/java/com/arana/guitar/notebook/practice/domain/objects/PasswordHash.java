package com.arana.guitar.notebook.practice.domain.objects;

public record PasswordHash(
        String hash,
        String salt,
        int iterations
) {}
