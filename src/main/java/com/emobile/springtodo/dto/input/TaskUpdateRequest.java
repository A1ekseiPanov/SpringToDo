package com.emobile.springtodo.dto.input;

import java.time.LocalDateTime;

public record TaskUpdateRequest(String title,
                                String description,
                                String status) {
}