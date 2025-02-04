package com.devtribe.devtribe_feed_service.user.application.validators;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("이메일 유효성 테스트")
class EmailValidatorTest {

    EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @DisplayName("유효한 이메일일 경우 이메일 생성에 성공해야한다.")
    @Test
    void validateSuccess() {
        String email = "a".repeat(EmailValidator.EMAIL_LOCAL_PART_MAX_LENGTH) + "@" + "a".repeat(
            EmailValidator.EMAIL_DOMAIN_PART_MAX_LENGTH - 5) + "-.com";
        assertThatCode(() -> validator.validateEmail(email))
            .doesNotThrowAnyException();
    }

    @DisplayName("Null 이거나 Empty 일 경우 이메일 생성시 예외가 발생해야한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateFailWithNullOrEmptyEmail(String email) {
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("@가 포함되지 않은 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithNoAtSymbol() {
        String email = "userexample.com";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("@가 한 개 이상 포함된 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithMoreThanOneAtSymbol() {
        String email = "user@@example.com";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로컬 파트 최대 길이가 넘는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithLocalPartLength() {
        String email =
            "a".repeat(EmailValidator.EMAIL_LOCAL_PART_MAX_LENGTH + 1) + "@" + "example.com";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("도메인 파트 최대 길이가 넘는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithDomainPartLength() {
        String email = "user@" + "a".repeat(EmailValidator.EMAIL_DOMAIN_PART_MAX_LENGTH + 1);
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로컬 파트가 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithLocalPartEmpty() {
        String email = "@example.com";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("도메인파트가 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithDomainPartEmpty() {
        String email = "username@";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("로컬 파트와 도메인 파트 모두 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithLocalAndDomainPartEmpty() {
        String email = "@";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로컬 파트에 허용되지 않은 특수 문자 포함한 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "user,name@example.com", "(username)@example.com", "user:name@example.com",
        "user;name@example.com", "[username]@example.com", "user$name@example.com",
        "<username>@example.com",})
    void validateFailWithNotAllowedLocalPartSpecialCharacter(String email) {
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("도메인 파트에 허용되지 않은 특수 문자 포함한 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "username@exam_ple.com", "user@example$name.com", "user@example!name.com",
        "user@example%name.com", "user@example^name.com", "user@example&name.com"
    })
    void validateFailWithNotAllowedDomainPartSpecialCharacter(String email) {
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("도메인 파트가 하이픈으로 시작하거나 끝나는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @ParameterizedTest
    @ValueSource(strings = {
        "username@-example",
        "username@example-",
    })
    void validateFailWithHyphen(String email) {
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("도메인 파트에 연속된 점이 포함된 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다.")
    @Test
    void validateFailWithDomainPartDoubleDots() {
        String email = "username@example..com";
        assertThatThrownBy(() -> validator.validateEmail(email))
            .isInstanceOf(IllegalArgumentException.class);
    }
}