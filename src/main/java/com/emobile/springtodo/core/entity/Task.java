package com.emobile.springtodo.core.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Task {
    private Long id;
    private String title;
    private String description;
    @Builder.Default
    private String status = Status.CREATED.toString();
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updated = LocalDateTime.now();
}