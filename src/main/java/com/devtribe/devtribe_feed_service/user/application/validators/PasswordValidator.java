package com.devtribe.devtribe_feed_service.user.application.validators;

import java.util.regex.Pattern;

public class PasswordValidator {

    public static final Integer PASSWORD_MIN_LENGTH = 8;
    public static final Integer PASSWORD_MAX_LENGTH = 64;
    private static final String PASSWORD_REGEX =
        "^(?=.*[a-z])"          // 소문자 최소 1개 포함
            + "(?=.*[A-Z])"     // 대문자 최소 1개 포함
            + "(?=.*\\d)"       // 숫자 최소 1개 포함
            + "(?=.*[@$!%*?&])" // 특수문자 최소 1개 포함
            + "[A-Za-z\\d@$!%*?&]+$";

    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 빈 값일 수 없습니다.");
        }

        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호의 길이가 유효하지 않습니다.");
        }

        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new IllegalArgumentException("비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다.");
        }
    }

}
