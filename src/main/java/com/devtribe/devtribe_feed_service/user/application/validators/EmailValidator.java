package com.devtribe.devtribe_feed_service.user.application.validators;

import com.google.common.base.Preconditions;
import java.util.regex.Pattern;

public class EmailValidator {

    public static final Integer EMAIL_MAX_LENGTH = 320;
    public static final Integer EMAIL_LOCAL_PART_MAX_LENGTH = 64;
    public static final Integer EMAIL_DOMAIN_PART_MAX_LENGTH = 255;

    private static final String EMAIL_REGEX = "^(?=.{1," + EMAIL_MAX_LENGTH + "}$)" // 전체 길이 검증
        + "(?=.{1," + EMAIL_LOCAL_PART_MAX_LENGTH + "}@)" // 로컬 파트 길이 검증
        + "(?=[^@]+@[^@]{1," + EMAIL_DOMAIN_PART_MAX_LENGTH + "}$)" // 도메인 파트 길이 검증
        + "^[a-zA-Z0-9._%+-]+@" // 로컬 파트 알파벳, 숫자, 점, 밑줄, 퍼센트, 플러스, 하이픈 허용
        + "(?!-)[a-zA-Z0-9-]+"  // 도메인 파트 시작에 하이픈 금지
        + "(?:\\.[a-zA-Z0-9-]+)*" // 도메인 점 구분 (연속된 점 금지)
        + "(?<!-)$"; // 도메인 끝에 하이픈 금지

    public void validateEmail(String email) {
        Preconditions.checkArgument(email != null, "이메일은 필수값입니다.");
        Preconditions.checkArgument(!email.isEmpty(), "이메일은 빈 값일 수 없습니다.");
        Preconditions.checkArgument(Pattern.matches(EMAIL_REGEX, email), "유효하지 않은 이메일 형식입니다.");
    }
}
