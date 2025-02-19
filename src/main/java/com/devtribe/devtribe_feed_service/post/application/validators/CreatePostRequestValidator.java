package com.devtribe.devtribe_feed_service.post.application.validators;

import org.springframework.stereotype.Component;

@Component
public class CreatePostRequestValidator {

    public static final Integer MAX_TITLE_LENGTH = 250;
    public static final Integer MAX_BODY_LENGTH = 1000;

    public void validateTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("제목은 필수값입니다.");
        }

        if (title.isEmpty()) {
            throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목은 " + MAX_TITLE_LENGTH + "자를 초과할 수 없습니다.");
        }
    }

    public void validateBody(String content) {
        if (content == null) {
            return;
        }

        if (content.length() > MAX_BODY_LENGTH) {
            throw new IllegalArgumentException("본문은 " + MAX_BODY_LENGTH + "자를 초과할 수 없습니다.");
        }
    }
}
