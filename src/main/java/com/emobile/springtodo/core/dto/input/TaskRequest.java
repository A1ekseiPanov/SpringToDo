package com.emobile.springtodo.core.dto.input;

import com.emobile.springtodo.core.annotation.UniqTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record TaskRequest(
        @Schema(example = "Refactoring")
        @NotBlank(message = "title should not be empty")
        @UniqTitle
        String title,
        @Schema(example = "Refactoring this code")
        @NotBlank(message = "description should not be empty")
        String description) implements Serializable {
}