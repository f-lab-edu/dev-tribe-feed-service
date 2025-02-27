package com.devtribe.devtribe_feed_service.post.application.validators

import com.devtribe.devtribe_feed_service.post.domain.ContentSource
import com.devtribe.devtribe_feed_service.post.domain.ContentType
import spock.lang.Specification

class PostRequestValidatorTest extends Specification {

    def validator = new PostRequestValidator()
    static def MAX_TITLE_LEN = PostRequestValidator.MAX_TITLE_LENGTH
    static def MAX_BODY_LEN = PostRequestValidator.MAX_BODY_LENGTH

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

    def "유효한 포스트의 ContentSource 주어질 경우 검증에 성공한다."() {
        when:
        def contentSource = new ContentSource(1L, ContentType.USER);
        validator.validateContentSource(contentSource)

        then:
        notThrown(IllegalArgumentException)
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

    def "contentSource가 null로 주어질 때, request 유효성 검증에 실패한다."() {
        given:
        def contentSource = null

        when:
        validator.validateContentSource(contentSource)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "컨텐츠 소스는 필수값입니다."
    }

    def "유효하지 않은 contentSource가 주어질 때, request 유효성 검증에 실패한다."() {
        given:
        def invalidContentSource = new ContentSource(contentId, contentType)

        when:
        validator.validateContentSource(invalidContentSource)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == expectedMessage

        where:
        contentId | contentType      || expectedMessage
        null      | ContentType.USER || "컨텐츠 식별자는 필수값입니다."
        1L        | null             || "컨텐츠 타입은 필수값입니다."
    }
}
