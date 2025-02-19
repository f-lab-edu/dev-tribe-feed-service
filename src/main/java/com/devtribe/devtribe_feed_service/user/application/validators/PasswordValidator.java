package com.devtribe.devtribe_feed_service.user.application.validators;

import com.google.common.base.Preconditions;
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
        Preconditions.checkArgument(password != null, "비밀번호는 필수 값입니다.");
        Preconditions.checkArgument(!password.isEmpty(), "비밀번호는 비어있을 수 없습니다.");
        Preconditions.checkArgument(password.length() >= PASSWORD_MIN_LENGTH,
            "비밀번호의 길이가 유효하지 않습니다.");
        Preconditions.checkArgument(password.length() <= PASSWORD_MAX_LENGTH,
            "비밀번호의 길이가 유효하지 않습니다.");
        Preconditions.checkArgument(Pattern.matches(PASSWORD_REGEX, password),
            "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다.");
    }
}
