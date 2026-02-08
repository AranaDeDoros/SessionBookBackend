package com.arana.guitar.notebook.practice.domain.util;

import com.arana.guitar.notebook.practice.domain.objects.PasswordHash;
import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    PasswordHash hash(String rawPassword);
    boolean matches(String rawPassword, PasswordHash storedHash);
}
