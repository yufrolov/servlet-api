package com.yufrolov.vocabulary.service;

import com.yufrolov.vocabulary.dto.VocabularyDTO;
import com.yufrolov.vocabulary.entity.Vocabulary;
import com.yufrolov.vocabulary.exception.DBException;
import com.yufrolov.vocabulary.exception.ValidationException;
import com.yufrolov.vocabulary.repository.VocabularyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VocabularyService {
    private static final Logger logger = Logger.getLogger(VocabularyService.class.getName());

    private static final Integer MIN_WORD_LENGTH = 1;
    private final VocabularyRepository vocabularyRepository;

    public VocabularyService(VocabularyRepository vocabularyRepository) {
        this.vocabularyRepository = vocabularyRepository;
    }

    private boolean isSatisfies(String word, String translateWord) {
        if (word != null && translateWord != null
                && word.length() >= MIN_WORD_LENGTH
                && translateWord.length() >= MIN_WORD_LENGTH) {
            return true;
        } else {
            throw new ValidationException("");
        }
    }

    public boolean add(Long idClient, String word, String translateWord) {
        try {
            if (isSatisfies(word, translateWord)) {
                return vocabularyRepository.create(idClient, word, translateWord);
            }
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Error parameter", e);
        }

        return false;
    }

    public boolean delete(Long profileId, Long id) {
        try {
            if (isWordByProfile(profileId, id)) {
                return vocabularyRepository.deleteById(id);
            }
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Error parameter", e);
        }
        return false;
    }

    private boolean isWordByProfile(Long profileId, Long id) {
        try {
            var vocabulary = vocabularyRepository.findById(id);
            if (vocabulary != null && profileId.equals(vocabulary.profile_id())) {
                return true;
            }
            throw new ValidationException();
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQL", e);
        }
        return false;
    }

    public boolean update(Long profileId, String word, String translateWord, Long id) {
        try {
            if (isWordByProfile(profileId, id) && isSatisfies(word, translateWord)) {
                return vocabularyRepository.updateById(word, translateWord, id);
            }
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Error parameter", e);
        }
        return false;
    }

    private VocabularyDTO mapToDTO(Vocabulary vocabulary) {
        return new VocabularyDTO(
                vocabulary.id()
                , vocabulary.word()
                , vocabulary.translate_word()
        );
    }

    public VocabularyDTO getVocabularyDTO(Long profileId, Long id) {
        try {
            var vocabulary = vocabularyRepository.findById(id);
            if (vocabulary != null && vocabulary.profile_id().equals(profileId)) {
                return mapToDTO(vocabulary);
            }
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        }
        return null;
    }

    public List<VocabularyDTO> vocabulariesDTO(Long idClient) {
        try {
            List<VocabularyDTO> receiptsDTO = new ArrayList<>();
            for (var val : vocabularyRepository.findByProfileId(idClient)) {
                receiptsDTO.add(mapToDTO(val));
            }
            return receiptsDTO;

        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        }
        return null;
    }
}
