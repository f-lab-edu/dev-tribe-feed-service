package com.devtribe.devtribe_feed_service.user.domain;

import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class Password {

    public static final Integer PASSWORD_MIN_LENGTH = 8;
    public static final Integer PASSWORD_MAX_LENGTH = 64;

    private static final String PASSWORD_REGEX =
        "^(?=.*[a-z])"          // 소문자 최소 1개 포함
            + "(?=.*[A-Z])"     // 대문자 최소 1개 포함
            + "(?=.*\\d)"       // 숫자 최소 1개 포함
            + "(?=.*[@$!%*?&])" // 특수문자 최소 1개 포함
            + "[A-Za-z\\d@$!%*?&]+$";

    private final String password;

    private Password(String password) {
        validate(password);
        this.password = password;
    }

    public static Password of(String password) {
        return new Password(password);
    }

    private void validate(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어 있거나 null일 수 없습니다.");
        }

        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new IllegalArgumentException("유효한 비밀번호가 아닙니다.");
        }

        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            throw new IllegalArgumentException("유효한 비밀번호가 아닙니다.");
        }
    }
}
