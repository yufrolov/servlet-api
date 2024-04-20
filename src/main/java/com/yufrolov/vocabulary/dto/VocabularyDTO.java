package com.yufrolov.vocabulary.dto;

public record VocabularyDTO(
        Long id,
        String word,
        String translate_word
) {
    @Override
    public String toString() {
        return "Словарь: " +
                "слово = " + word +
                ", перевод = " + translate_word;
    }
}
