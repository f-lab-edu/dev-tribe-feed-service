package com.devtribe.unit.user.application.validator

import com.devtribe.domain.user.application.validators.EmailValidator
import spock.lang.Specification

class EmailValidatorTest extends Specification {

    def validator = new EmailValidator()

    def "유효한 이메일 경우 이메일 생성에 성공해야한다."() {
        given:
        String email = "a".repeat(EmailValidator.EMAIL_LOCAL_PART_MAX_LENGTH) +
                "@" + "a".repeat(EmailValidator.EMAIL_DOMAIN_PART_MAX_LENGTH - 5) +
                "-.com"

        when:
        validator.validateEmail(email)

        then:
        notThrown(Exception)
    }

    def "Null 이거나 Empty 일 경우 이메일 생성시 예외가 발생해야한다."() {
        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == exceptedMessage

        where:
        email || exceptedMessage
        null  || "이메일은 필수값입니다."
        ""    || "이메일은 빈 값일 수 없습니다."
    }

    def "@가 포함되지 않은 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "userexample.com"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "@가 한 개 이상 포함된 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "user@@example.com"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "로컬 파트 최대 길이가 넘는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "a".repeat(EmailValidator.EMAIL_LOCAL_PART_MAX_LENGTH + 1) + "@" + "example.com"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "도메인 파트 최대 길이가 넘는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "user@" + "a".repeat(EmailValidator.EMAIL_DOMAIN_PART_MAX_LENGTH + 1)

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "로컬 파트가 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "@example.com"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "도메인파트가 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "username@"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "로컬 파트와 도메인 파트 모두 빈 문자열인 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "@"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }

    def "로컬 파트에 허용되지 않은 특수 문자 포함한 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."

        where:
        email << ["user,name@example.com", "(username)@example.com", "user:name@example.com",
                  "user;name@example.com", "[username]@example.com", "user\$name@example.com",
                  "<username>@example.com"]
    }

    def "도메인 파트에 허용되지 않은 특수 문자 포함한 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."

        where:
        email << ["username@exam_ple.com", "user@example\$name.com", "user@example!name.com",
                  "user@example%name.com", "user@example^name.com", "user@example&name.com"]
    }

    def "도메인 파트가 하이픈으로 시작하거나 끝나는 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."

        where:
        email << ["username@-example", "username@example-"]
    }

    def "도메인 파트에 연속된 점이 포함된 이메일이 주어졌을 때 이메일 생성시 예외가 발생해야한다."() {
        given:
        String email = "username@example..com"

        when:
        validator.validateEmail(email)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "유효하지 않은 이메일 형식입니다."
    }
}
