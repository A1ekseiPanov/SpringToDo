package com.emobile.springtodo.dto.input;

import lombok.Builder;

@Builder
public record TaskRequest(String title,
                          String description) {
}