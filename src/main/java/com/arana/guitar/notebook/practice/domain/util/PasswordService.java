package com.arana.guitar.notebook.practice.domain.util;

import com.arana.guitar.notebook.practice.domain.objects.PasswordHash;

public interface PasswordService {
    PasswordHash hash(String rawPassword);
    boolean matches(String rawPassword, PasswordHash storedHash);
}
