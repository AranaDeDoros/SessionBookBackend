package com.arana.guitar.notebook.practice.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
@Data
public class PracticeSessionRequest {
    @NotBlank
    String name;
    @NotBlank
    String description;
    @NotEmpty
    List<Long> songIds;
}
