package com.devtribe.devtribe_feed_service.unit.post.application.validators

import com.devtribe.devtribe_feed_service.post.application.validators.FeedRequestValidator
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.FeedFilterOptionFixture
import spock.lang.Specification
import spock.lang.Title

import java.time.LocalDateTime

@Title("피드 요청 유효성 검사 테스트")
class FeedRequestValidatorTest extends Specification {

    def validator = new FeedRequestValidator()
    static def MAX_KEYWORD_LENGTH = FeedRequestValidator.MAX_KEYWORD_LENGTH
    static def MAX_PAGE_SIZE = FeedRequestValidator.MAX_PAGE_SIZE

    def "정렬 옵션이 null 값이어서는 안된다"() {
        when:
        validator.validateSortOption(null)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "유효하지 않은 요청 값입니다."
    }

    def "필터 옵션이 null 값이어서는 안된다"() {
        when:
        validator.validateFilterOption(null)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "유효하지 않은 요청 값입니다."
    }

    def "키워드가 null이거나, 길이가 최대 길이를 넘지 않는다면 검증에 성공한다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(keyword: keyword)

        when:
        validator.validateFilterOption(request)

        then:
        notThrown(IllegalArgumentException)

        where:
        keyword << ["a".repeat(MAX_KEYWORD_LENGTH), null]
    }

    def "키워드의 길이는 정해진 최대 길이를 넘을 수 없다"() {
        given:
        String keyword = "a".repeat(MAX_KEYWORD_LENGTH + 1)
        def request = FeedFilterOptionFixture.createFeedFilterOption(keyword: keyword)

        when:
        validator.validateFilterOption(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "최대 키워드 길이 " + MAX_KEYWORD_LENGTH + "자를 넘을 수 없습니다."
    }

    def "시작날짜가 종료날짜가 모두 null이어도 검증에 성공한다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(
                startDate: null,
                endDate: null
        )

        when:
        validator.validateFilterOption(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "시작날짜가 종료날짜와 같거나 이전이라면 검증에 성공한다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(
                startDate: startDate,
                endDate: endDate
        )

        when:
        validator.validateFilterOption(request)

        then:
        notThrown(IllegalArgumentException)

        where:
        startDate           | endDate
        LocalDateTime.now() | LocalDateTime.now()
        LocalDateTime.now() | LocalDateTime.now().plusMinutes(1)
    }

    def "시작날짜와 종료날짜 둘 중 하나라도 null이어서는 안된다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(
                startDate: startDate,
                endDate: endDate
        )

        when:
        validator.validateFilterOption(request)

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
        def request = FeedFilterOptionFixture.createFeedFilterOption(
                startDate: startDate,
                endDate: endDate
        )

        when:
        validator.validateFilterOption(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "시작 날짜는 종료 날짜보다 이전이어야 합니다."

        where:
        startDate                          | endDate
        LocalDateTime.now().plusMinutes(1) | LocalDateTime.now()
    }

    def "작성자 id는 null이 될 수 있다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(authorId: null)

        when:
        validator.validateFilterOption(request)

        then:
        notThrown(IllegalArgumentException)
    }

    def "작성자 id는 음수가 될 수 없다"() {
        given:
        def request = FeedFilterOptionFixture.createFeedFilterOption(authorId: -1L)

        when:
        validator.validateFilterOption(request)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "작성자가 유효하지 않습니다."
    }

    def "페이지 수의 범위가 최대 길이를 넘지 않는다면 검증에 성공한다"() {
        when:
        validator.validatePagination(MAX_PAGE_SIZE)

        then:
        notThrown(IllegalArgumentException)
    }

    def "페이지 수의 범위가 최대 길이를 넘을 수 없다"() {
        when:
        validator.validatePagination(MAX_PAGE_SIZE + 1)

        then:
        def result = thrown(IllegalArgumentException)
        result.getMessage() == "요청한 페이지 수의 범위가 올바르지 않습니다."
    }
}
