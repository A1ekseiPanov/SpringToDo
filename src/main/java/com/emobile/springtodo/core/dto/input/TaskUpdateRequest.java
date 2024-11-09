package com.emobile.springtodo.core.dto.input;

import com.emobile.springtodo.core.annotation.CheckStatus;
import com.emobile.springtodo.core.annotation.UniqTitle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record TaskUpdateRequest(
        @Schema(example = "1111")
        @NotNull(message = "task id should not be empty")
        @Positive(message = "id cannot be less than 1")
        Long id,
        @Schema(example = "Refactoring")
        @NotBlank(message = "title should not be empty")
        @UniqTitle
        String title,
        @Schema(example = "Refactoring this code")
        @NotBlank(message = "description should not be empty")
        String description,
        @Schema(example = "CREATED")
        @CheckStatus(message = "status must correspond to: 'CREATED', 'DO_WORK', 'COMPLETED'")
        @NotBlank(message = "status should not be empty")
        String status) implements Serializable {
}