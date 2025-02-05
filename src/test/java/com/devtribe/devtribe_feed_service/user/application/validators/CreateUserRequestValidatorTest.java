package com.devtribe.devtribe_feed_service.user.application.validators;

import static com.devtribe.devtribe_feed_service.user.application.validators.CreateUserRequestValidator.MAX_BIOGRAPHY_LENGTH;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("유저 생성 요청 유효성 테스트")
class CreateUserRequestValidatorTest {

    CreateUserRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CreateUserRequestValidator();
    }

    @DisplayName("자기소개는 Null이거나 Empty여도 유저 정보 생성에 성공해야한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateSuccessWithNullOrEmptyBiography(String biography) {
        assertThatCode(() -> validator.validateBiography(biography))
            .doesNotThrowAnyException();
    }

    @DisplayName("자기소개의 최대 길이가 넘는 자기소개가 주어졌을때 유저 정보 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithBiographyOverMaxLength() {
        String biography = "a".repeat(MAX_BIOGRAPHY_LENGTH + 1);

        assertThatThrownBy(() -> validator.validateBiography(biography))
            .isInstanceOf(IllegalArgumentException.class);
    }
}