package com.emobile.springtodo.core.dto.output;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(Long id,
                           String title,
                           String description,
                           String status,
                           LocalDateTime created,
                           LocalDateTime updated) {
}