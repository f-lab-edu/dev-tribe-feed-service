package com.devtribe.unit.post.application.validators


import com.devtribe.domain.post.application.validators.PostRequestValidator
import spock.lang.Specification

import static com.devtribe.fixtures.post.dto.PostSearchRequestFixture.createPostQueryRequest

class PostSearchValidateTest extends Specification {

    def validator = new PostRequestValidator()

    static def MAX_KEYWORD_LENGTH = PostRequestValidator.MAX_KEYWORD_LENGTH

    def "키워드가 null일 경우 검증에 실패한다."(){
        given:
        def request = createPostQueryRequest(keyword: null)

        when:
        validator.validateSearchRequest(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "키워드는 필수값입니다."
    }

    def "키워드 길이가 최대 길이를 넘지 않는다면 검증에 성공한다"() {
        given:
        def keyword = "a".repeat(MAX_KEYWORD_LENGTH)
        def request = createPostQueryRequest(keyword: keyword)

        when:
        validator.validateSearchRequest(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "키워드의 길이는 정해진 최대 길이를 넘을 수 없다"() {
        given:
        String keyword = "a".repeat(MAX_KEYWORD_LENGTH + 1)
        def request = createPostQueryRequest(keyword: keyword)

        when:
        validator.validateSearchRequest(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "최대 키워드 길이 " + MAX_KEYWORD_LENGTH + "자를 넘을 수 없습니다."
    }
}
