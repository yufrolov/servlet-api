package com.yufrolov.vocabulary.entity;

public record Profile(
        Long id,
        String login,
        String password,
        String fio
) {
}
