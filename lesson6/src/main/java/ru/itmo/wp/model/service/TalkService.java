package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public List<Talk> findByUserId(long userId) {
        return talkRepository.findByUserId(userId);
    }

    public void save(Talk talk) { talkRepository.save(talk); }

    public void validateTalk(Talk talk) throws ValidationException {
        if (userRepository.find(talk.getSourceUserId()) == null) {
            throw new ValidationException("Invalid source user login");
        }
        if (userRepository.find(talk.getTargetUserId()) == null) {
            throw new ValidationException("Invalid target user login");
        }
        if (talk.getText() == null || talk.getText().trim().isEmpty()) {
            throw new ValidationException("Message is required");
        }
    }
}
