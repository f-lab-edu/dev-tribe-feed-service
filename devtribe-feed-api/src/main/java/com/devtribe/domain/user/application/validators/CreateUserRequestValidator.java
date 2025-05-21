package com.devtribe.domain.user.application.validators;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
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
