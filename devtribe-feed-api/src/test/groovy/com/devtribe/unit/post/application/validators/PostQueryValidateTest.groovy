package com.devtribe.unit.post.application.validators

import com.devtribe.domain.post.application.validators.PostRequestValidator
import spock.lang.Specification

import java.time.LocalDateTime

import static com.devtribe.fixtures.post.dto.PostQueryRequestFixture.createPostQueryRequest

class PostQueryValidateTest extends Specification {

    def validator = new PostRequestValidator()
    def MAX_PAGE_SIZE = PostRequestValidator.MAX_PAGE_SIZE

    def "시작날짜가 종료날짜가 모두 null이어도 검증에 성공한다"() {
        given:
        def request = createPostQueryRequest(startDate: null, endDate: null)

        when:
        validator.validateQueryRequest(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "시작날짜가 종료날짜와 같거나 이전이라면 검증에 성공한다"() {
        given:
        def request = createPostQueryRequest(startDate: startDate, endDate: endDate)

        when:
        validator.validateQueryRequest(request)

        then:
        notThrown(IllegalArgumentException)

        where:
        startDate           | endDate
        LocalDateTime.now() | LocalDateTime.now()
        LocalDateTime.now() | LocalDateTime.now().plusMinutes(1)
    }

    def "시작날짜와 종료날짜 둘 중 하나라도 null이어서는 안된다"() {
        given:
        def request = createPostQueryRequest(startDate: startDate, endDate: endDate)

        when:
        validator.validateQueryRequest(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "날짜 범위가 유효하지 않습니다."

        where:
        startDate           | endDate
        LocalDateTime.now() | null
        null                | LocalDateTime.now()
    }

    def "시작 날짜는 종료날짜 이후가 될 수 없다"() {
        given:
        def request = createPostQueryRequest(startDate: startDate, endDate: endDate)

        when:
        validator.validateQueryRequest(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "시작 날짜는 종료 날짜보다 이전이어야 합니다."

        where:
        startDate                          | endDate
        LocalDateTime.now().plusMinutes(1) | LocalDateTime.now()
    }

    def "작성자 id는 null이 될 수 있다"() {
        given:
        def request = createPostQueryRequest(authorId: null)

        when:
        validator.validateQueryRequest(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "작성자 id는 음수가 될 수 없다"() {
        given:
        def request = createPostQueryRequest(authorId: -1L)

        when:
        validator.validateQueryRequest(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "작성자가 유효하지 않습니다."
    }

    def "페이지 수의 범위가 최대 길이를 넘지 않는다면 검증에 성공한다"() {
        given:
        def request = createPostQueryRequest(size: MAX_PAGE_SIZE)

        when:
        validator.validateQueryRequest(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "페이지 수의 범위가 최대 길이를 넘을 수 없다"() {
        given:
        def request = createPostQueryRequest(size: MAX_PAGE_SIZE + 1)

        when:
        validator.validateQueryRequest(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "요청한 페이지 수의 범위가 올바르지 않습니다. 최대 요청 가능한 페이지 수는 " + MAX_PAGE_SIZE + " 입니다."
    }
}
