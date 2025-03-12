package com.devtribe.devtribe_feed_service.post.api

import com.devtribe.devtribe_feed_service.global.common.CursorMetadata
import com.devtribe.devtribe_feed_service.post.application.FeedService
import com.devtribe.devtribe_feed_service.post.application.dtos.GetFeedResponse
import com.devtribe.devtribe_feed_service.post.application.dtos.PostResponse
import com.devtribe.devtribe_feed_service.test.fixtures.PostResponseFixture
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.util.LinkedMultiValueMap
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Title(value = "피드 조회 컨트롤러 테스트")
@WebMvcTest(FeedController.class)
class FeedControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    FeedService feedService = Mock()

    def "피드 정렬 조회 성공 - 200 status와 피드 리스트 반환"() {
        given:
        def requestParams = new LinkedMultiValueMap<String, String>()
        requestParams.add("cursor", "1")
        requestParams.add("pageSize", "10")
        requestParams.add("sort", "latest")
        def response = new GetFeedResponse(
                PostResponseFixture.createLatestPostResponse(10),
                new CursorMetadata(null, 10, false))
        feedService.getFeedListBySortOption(_, _) >> response

        when:
        def result = mockMvc.perform(get("/api/v1/feeds")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestParams))
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        def responseBody = objectMapper.readTree(content)

        result.response.status == HttpStatus.OK.value()
        (responseBody.get("data") as List<PostResponse>).size() == 10
        responseBody.get("cursorMetaData").get("nextCursor").isNull()
        responseBody.get("cursorMetaData").get("totalCount").asInt() == 10
        !responseBody.get("cursorMetaData").get("hasNextPage")
    }

    def "피드 정렬 조회 실패 - 올바르지 않은 파라미터, 400 status와 에러메시지 반환"() {
        given:
        def requestParams = new LinkedMultiValueMap<String, String>()
        requestParams.add("cursor", "1")
        requestParams.add("pageSize", pageSize)
        requestParams.add("sort", sort)
        feedService.getFeedListBySortOption(_, _) >> { throw new IllegalArgumentException(exceptionMessage) }

        when:
        def result = mockMvc.perform(get("/api/v1/feeds")
                .contentType(MediaType.APPLICATION_JSON)
                .params(requestParams))
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        def responseBody = objectMapper.readTree(content)

        result.response.status == HttpStatus.BAD_REQUEST.value()
        responseBody.get("errorMessage").asText() == exceptionMessage

        where:
        pageSize | sort          || exceptionMessage
        "10"     | "invalidSort" || "올바른 정렬 값이 아닙니다."
        "999999" | "oldest"      || "유효하지 않은 요청 값입니다."
    }
}
