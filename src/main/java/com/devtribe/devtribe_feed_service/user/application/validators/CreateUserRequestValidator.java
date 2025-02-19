package com.devtribe.devtribe_feed_service.user.application.validators;

import com.google.common.base.Preconditions;

public class CreateUserRequestValidator {

    public static final Integer MAX_BIOGRAPHY_LENGTH = 100;

    public void validateBiography(String biography) {
        if (biography == null) {
            return;
        }

        Preconditions.checkArgument(biography.length() <= MAX_BIOGRAPHY_LENGTH,
            "자기소개는 " + MAX_BIOGRAPHY_LENGTH + "자를 초과할 수 없습니다.");
    }
}
