package com.devtribe.devtribe_feed_service.post.application.validators

import spock.lang.Specification

class CreatePostRequestValidatorTest extends Specification {

    def validator = new CreatePostRequestValidator()
    static def MAX_TITLE_LEN = CreatePostRequestValidator.MAX_TITLE_LENGTH
    static def MAX_BODY_LEN = CreatePostRequestValidator.MAX_BODY_LENGTH

    def "유효한 포스트의 제목이 주어질 경우 검증에 성공한다."() {
        when:
        validator.validateTitle(title)

        then:
        notThrown(IllegalArgumentException)

        where:
        title << ["t", "title", "t".repeat(MAX_TITLE_LEN)]
    }

    def "유효한 포스트의 본문이 주어질 경우 검증에 성공한다."() {
        when:
        validator.validateBody(title)

        then:
        notThrown(IllegalArgumentException)

        where:
        title << [null, "", "c", "content", "c".repeat(MAX_BODY_LEN)]
    }

    def "#invalid 포스트의 제목이 주어질 경우 예외가 발생해야 한다."() {
        when:
        validator.validateTitle(title)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == message

        where:
        invalid      | title                         | message
        "null 값인"    | null                          | "제목은 필수값입니다."
        "빈값인"        | ""                            | "제목은 비어있을 수 없습니다."
        "유효길이를 초과하는" | "a".repeat(MAX_TITLE_LEN + 1) | "제목은 " + MAX_TITLE_LEN + "자를 초과할 수 없습니다."
    }

    def "유효하지 않은 포스트의 본문이 주어질 경우 예외가 발생해야 한다."() {
        when:
        validator.validateBody("a".repeat(MAX_BODY_LEN + 1))

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "본문은 " + MAX_BODY_LEN + "자를 초과할 수 없습니다."
    }
}
