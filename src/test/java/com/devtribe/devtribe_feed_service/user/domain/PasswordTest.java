package com.devtribe.devtribe_feed_service.user.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("비밀번호 유효성 테스트")
class PasswordTest {

    @DisplayName("유효한 비밀번호일 경우 비밀번호 생성에 성공해야한다.")
    @Test
    void validateSuccess() {
        String password = "A1b!5678";
        assertThatCode(() -> Password.of(password))
            .doesNotThrowAnyException();
    }

    @DisplayName("Null 이거나 Empty 일 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateFailWithMinimumLength(String password) {
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 비밀번호 길이보다 작은 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMinimumLength() {
        String password = "Aa1!aa";
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최대 비밀번호 길이보다 큰 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMaximumLength() {
        String password = "A1!" + "a".repeat(Password.PASSWORD_MAX_LENGTH - 2);
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("대문자가 없는 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMissingUppercase() {
        String password = "abcd1234!";
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("소문자가 없는 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMissingLowercase() {
        String password = "ABCD1234!";
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숫자가 없는 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMissingDigit() {
        String password = "ABCDEFGH!";
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("특수문자가 없는 경우 비밀번호 생성 시 예외가 발생해야 한다.")
    @Test
    void validateFailWithMissingSpecialCharacter() {
        String password = "ABCD1234";
        assertThatThrownBy(() -> Password.of(password))
            .isInstanceOf(IllegalArgumentException.class);
    }

}