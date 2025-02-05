package com.devtribe.devtribe_feed_service.user.application.validators

import spock.lang.Specification

import static com.devtribe.devtribe_feed_service.user.application.validators.CreateUserRequestValidator.MAX_BIOGRAPHY_LENGTH

class CreateUserRequestValidatorTest extends Specification {

    def validator = new CreateUserRequestValidator()

    def "자기소개는 Null이거나 Empty여도 유저 정보 생성에 성공해야한다."(){
        when:
        validator.validateBiography(biography)

        then:
        notThrown(Exception)

        where:
        biography << [null, ""]
    }

    def "자기소개의 최대 길이가 넘는 자기소개가 주어졌을때 유저 정보 생성시 예외가 발생해야한다."(){
        given:
        String biography = "a".repeat(MAX_BIOGRAPHY_LENGTH + 1);

        when:
        validator.validateBiography(biography)

        then:
        thrown(IllegalArgumentException)
    }
}
