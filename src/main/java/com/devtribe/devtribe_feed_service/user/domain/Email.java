package com.devtribe.devtribe_feed_service.user.domain;

import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Email {

    public static final Integer EMAIL_MAX_LENGTH = 320;
    public static final Integer EMAIL_LOCAL_PART_MAX_LENGTH = 64;
    public static final Integer EMAIL_DOMAIN_PART_MAX_LENGTH = 255;

    private static final String EMAIL_REGEX =
        "^[a-zA-Z0-9._%+-]+@" +         // 로컬 파트: 알파벳, 숫자, 점, 밑줄, 퍼센트, 플러스, 하이픈 허용
            "(?!-)[a-zA-Z0-9-]+" +      // 도메인 시작에 하이픈 금지
            "(?:\\.[a-zA-Z0-9-]+)*" +   // 도메인 점 구분 (연속된 점 금지)
            "(?<!-)$";                  // 도메인 끝에 하이픈 금지

    private final String email;

    private Email(String email) {
        validate(email);
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validate(String email) {
        validateEmailLength(email);
        validateEmailParts(email);
        validateEmailRegex(email);
    }

    private void validateEmailLength(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 비어 있거나 null일 수 없습니다.");
        }

        if (email.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    private void validateEmailParts(String email) {
        String[] parts = email.split("@");

        if (parts.length != 2) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }

        String localPart = parts[0];
        String domainPart = parts[1];

        if (localPart.isEmpty() || localPart.length() > EMAIL_LOCAL_PART_MAX_LENGTH) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
        if (domainPart.isEmpty() || domainPart.length() > EMAIL_DOMAIN_PART_MAX_LENGTH) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }
    }

    private void validateEmailRegex(String email) {
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("이메일에 허용되지 않는 문자가 포함되어 있습니다.");
        }
    }
}
