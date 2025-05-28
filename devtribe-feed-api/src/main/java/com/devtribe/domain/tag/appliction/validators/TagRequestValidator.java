package com.devtribe.domain.tag.appliction.validators;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class TagRequestValidator {

    public static final int MAX_TAG_NAME_LENGTH = 10;

    public void validateTagNameLength(String tagName) {
        Preconditions.checkNotNull(tagName);
        Preconditions.checkArgument(tagName.length() < MAX_TAG_NAME_LENGTH,
            "태그 이름의 길이는 " + MAX_TAG_NAME_LENGTH + "자를 초과할 수 없습니다.");
    }
}
