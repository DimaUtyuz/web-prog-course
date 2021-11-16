package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.form.NoticeCredentials;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.service.NoticeService;

@Component
public class NoticeCredentialsAddValidator implements Validator {
    private final NoticeService noticeService;

    public NoticeCredentialsAddValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {}
}
