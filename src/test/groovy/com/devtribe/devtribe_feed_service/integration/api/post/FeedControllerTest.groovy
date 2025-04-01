package com.devtribe.devtribe_feed_service.integration.api.post

import com.devtribe.devtribe_feed_service.global.common.PageResponse
import com.devtribe.devtribe_feed_service.post.api.FeedController
import com.devtribe.devtribe_feed_service.post.application.FeedService
import com.devtribe.devtribe_feed_service.test.config.NoSecurityWebMvcTest
import com.devtribe.devtribe_feed_service.test.config.TestSecurityConfig
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.PostResponseFixture
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Title(value = "피드 조회 컨트롤러 테스트")
@NoSecurityWebMvcTest(controllers = FeedController.class)
@Import(TestSecurityConfig.class)
class FeedControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    FeedService feedService = Mock()

    def "피드 정렬 조회 성공 - 200 status 반환"() {
        given:
        def api = "/api/v1/feeds?cursorId=1&size=10&sort=NEWEST"
        def response = new PageResponse<>(
                PostResponseFixture.createLatestPostResponse(10),
                11L,
                10,
                false
        )
        feedService.getFeedListBySortOption(_, _) >> response

        when:
        def result = mockMvc.perform(get(api)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result.response.status == HttpStatus.OK.value()
    }

    def "피드 정렬 조회 실패 - 올바르지 않은 정렬 옵션, 400 status와 에러메시지 반환"() {
        given:
        def api = "/api/v1/feeds?cursorId=1&size=10&sort=invalidSort"
        feedService.getFeedListBySortOption(_, _) >> { throw new MethodArgumentTypeMismatchException("올바르지 않은 요청 값입니다.") }

        when:
        def result = mockMvc.perform(get(api)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"올바르지 않은 요청 값입니다.\"}"""
    }

    def "피드 정렬 조회 실패 - 페이지 크기 초과, 400 status와 에러메시지 반환"() {
        given:
        def api = "/api/v1/feeds?cursorId=1&size=999999&sort=OLDEST"
        feedService.getFeedListBySortOption(_, _) >> { throw new IllegalArgumentException("요청한 페이지 수의 범위가 올바르지 않습니다.") }

        when:
        def result = mockMvc.perform(get(api)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"요청한 페이지 수의 범위가 올바르지 않습니다.\"}"""
    }
}
