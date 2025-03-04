package com.devtribe.devtribe_feed_service.post.application.validators;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class CreatePostRequestValidator {

    public static final Integer MAX_TITLE_LENGTH = 250;
    public static final Integer MAX_BODY_LENGTH = 1000;

    public void validateTitle(String title) {
        Preconditions.checkArgument(title != null, "제목은 필수값입니다.");
        Preconditions.checkArgument(!title.isEmpty(), "제목은 비어있을 수 없습니다.");
        Preconditions.checkArgument(title.length() <= MAX_TITLE_LENGTH,
            "제목은 " + MAX_TITLE_LENGTH + "자를 초과할 수 없습니다.");
    }

    public void validateBody(String content) {
        if (content == null) {
            return;
        }

        Preconditions.checkArgument(content.length() <= MAX_BODY_LENGTH,
            "본문은 " + MAX_BODY_LENGTH + "자를 초과할 수 없습니다.");
    }
}
