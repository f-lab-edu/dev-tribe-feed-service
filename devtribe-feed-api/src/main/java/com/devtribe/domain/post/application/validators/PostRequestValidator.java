package com.devtribe.domain.post.application.validators;

import com.google.common.base.Preconditions;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostRequestValidator {

    public static final Integer MAX_TITLE_LENGTH = 250;
    public static final Integer MAX_BODY_LENGTH = 1000;
    public static final Integer MAX_TAG_LIST_SIZE = 10;

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

    public void validateTagListSize(List<Long> tags) {
        Preconditions.checkNotNull(tags);
        Preconditions.checkArgument(tags.size() <= MAX_TAG_LIST_SIZE,
            "태그는 최대 " + MAX_TAG_LIST_SIZE + "개까지 등록할 수 있습니다");
    }
}
