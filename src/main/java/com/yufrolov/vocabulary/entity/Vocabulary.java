package com.yufrolov.vocabulary.entity;

public record Vocabulary(
        Long id,
        Long profile_id,
        String word,
        String translate_word
) {
}
