package com.yufrolov.vocabulary.service;

import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.exception.DBException;
import com.yufrolov.vocabulary.exception.ValidationException;
import com.yufrolov.vocabulary.repository.ProfileRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfileService {
    final int MIN_LOGIN_LENGTH = 8;
    final int MIN_PASSWORD_LENGTH = 8;
    final int MIN_FIO_LENGTH = 3;

    private static final Logger logger = Logger.getLogger(ProfileService.class.getName());

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private boolean isSatisfies(String fio, String login, String password) {
        if (fio != null && login != null && password != null
                && fio.length() >= MIN_FIO_LENGTH
                && login.length() >= MIN_LOGIN_LENGTH
                && password.length() >= MIN_PASSWORD_LENGTH) {
            return true;
        } else {
            throw new ValidationException();
        }
    }

    private boolean isSatisfies(String login, String password) {
        if (login != null && password != null
                && login.length() >= MIN_LOGIN_LENGTH
                && password.length() >= MIN_PASSWORD_LENGTH) {
            return true;
        } else {
            throw new ValidationException();
        }
    }


    public boolean doSignUp(String fio, String login, String password) {
        try {
            if (isSatisfies(fio, login, password)) {
                if (profileRepository.findByLoginAndPassword(login, password) == null) {
                    return profileRepository.create(fio, login, password);
                }
            }
        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Error parameter", e);
        }
        return false;
    }

    public Profile doLogin(String login, String password) {
        try {
            if (isSatisfies(login, password)) {
                return profileRepository.findByLoginAndPassword(login, password);
            }

        } catch (DBException e) {
            logger.log(Level.WARNING, "Error SQl", e);
        } catch (ValidationException e) {
            logger.log(Level.WARNING, "Error parameter", e);
        }
        return null;
    }
}
