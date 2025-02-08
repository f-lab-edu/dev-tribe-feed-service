package com.devtribe.devtribe_feed_service.user.application.validators

import spock.lang.Specification

class PasswordValidatorTest extends Specification {

    def validator = new PasswordValidator()

    def "유효한 비밀번호일 경우 비밀번호 생성에 성공해야한다."() {
        given:
        String password = "A1b!5678";

        when:
        validator.validatePassword(password)

        then:
        notThrown(Exception)
    }

    def "Null 이거나 Empty 일 경우 비밀번호 생성 시 예외가 발생해야 한다."() {
        when:
        validator.validatePassword(password)

        then:
        thrown(IllegalArgumentException)

        where:
        password << [null, ""]
    }

    def "비밀번호 길이를 만족하지 않을 경우 예외가 발생해야 한다."() {
        when:
        validator.validatePassword(password)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == expectedMessage

        where:
        password                                                      || expectedMessage
        "Aa1!aa"                                                      || "비밀번호의 길이가 유효하지 않습니다."
        "A1!" + "a".repeat(PasswordValidator.PASSWORD_MAX_LENGTH - 2) || "비밀번호의 길이가 유효하지 않습니다."
    }

    def "대문자/소문자/숫자/특수문가 없는 경우 비밀번호 생성 시 예외가 발생해야 한다."() {
        when:
        validator.validatePassword(password)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == exceptedMessage

        where:
        password    || exceptedMessage
        "abcd1234!" || "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다."
        "ABCD1234!" || "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다."
        "ABCDEFGH!" || "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다."
        "ABCD1234"  || "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다."
    }
}
