package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.FeedController
import com.devtribe.domain.post.application.FeedService
import com.devtribe.fixtures.post.dto.PostResponseFixture
import com.devtribe.global.model.PageResponse
import com.devtribe.integration.config.NoSecurityWebMvcTest
import com.devtribe.integration.config.TestSecurityConfig
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@Title(value = "피드 조회 컨트롤러 테스트")
@NoSecurityWebMvcTest(controllers = FeedController.class)
@Import(TestSecurityConfig.class)
class FeedControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    FeedService feedService = Mock()

    def "피드 검색 조회 성공 - 200 status 반환"() {
        given:
        def api = "/api/v1/feeds/search"
        String request = """
        {
          "filter": {
            "keyword": "검색어 예시",
            "dateRange": {
              "startDate": "2025-04-01T00:00:00",
              "endDate": "2025-04-30T23:59:59"
            },
            "authorId": 1
          },
          "sort": "NEWEST",
          "offset": 0,
          "size": 10
        }
        """.stripIndent()
        def response = new PageResponse<>(PostResponseFixture.createLatestPostResponse(10), 0)
        feedService.findFeedBySearchOption(_) >> response

        when:
        def result = mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn()

        then:
        result.response.status == HttpStatus.OK.value()
    }

    def "피드 정렬 조회 실패 - 올바르지 않은 정렬 옵션, 400 status와 에러메시지 반환"() {
        given:
        def api = "/api/v1/feeds/search"
        String request = """
        {
          "filter": {
            "keyword": "검색어 예시",
            "dateRange": {
              "startDate": "2025-04-01T00:00:00",
              "endDate": "2025-04-30T23:59:59"
            },
            "authorId": 1
          },
          "sort": "INVALID",
          "offset": 0,
          "size": 10
        }
        """.stripIndent()

        when:
        def result = mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"올바르지 않은 요청 값입니다.\"}"""
    }

    def "피드 정렬 조회 실패 - 페이지 크기 초과, 400 status와 에러메시지 반환"() {
        given:
        def api = "/api/v1/feeds/search"
        String request = """
        {
          "filter": {
            "keyword": "검색어 예시",
            "dateRange": {
              "startDate": "2025-04-01T00:00:00",
              "endDate": "2025-04-30T23:59:59"
            },
            "authorId": 1
          },
          "sort": "NEWEST",
          "offset": 0,
          "size": 99999
        }
        """.stripIndent()

        feedService.findFeedBySearchOption(_) >> { throw new IllegalArgumentException("요청한 페이지 수의 범위가 올바르지 않습니다.") }

        when:
        def result = mockMvc.perform(post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"요청한 페이지 수의 범위가 올바르지 않습니다.\"}"""
    }
}
