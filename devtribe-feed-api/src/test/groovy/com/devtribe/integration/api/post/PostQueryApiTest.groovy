package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.PostController
import com.devtribe.domain.post.application.PostService
import com.devtribe.integration.config.NoSecurityWebMvcTest
import com.devtribe.integration.config.TestSecurityConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Title(value = "게시글 조회 API 테스트")
@Import(TestSecurityConfig.class)
@NoSecurityWebMvcTest(controllers = PostController.class)
class PostQueryApiTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    PostService postService = Mock(PostService)

    @Shared
    final String API = "/api/v1/posts"

    def "게시글 정렬 조회 실패 - 올바르지 않은 정렬 옵션, 400 status와 에러메시지 반환"() {
        when:
        def result = mockMvc.perform(
                get(API).param("startDate", "2025-04-01T00:00:00")
                        .param("endDate", "2025-04-30T23:59:59")
                        .param("authorId", "1")
                        .param("sort", "INVALID")
                        .param("page", "0")
                        .param("size", "10"))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"유효하지 않은 요청값입니다. 요청 파라미터를 다시 확인해주세요.\"}"""
    }

    def "게시글 정렬 조회 실패 - 페이지 크기 초과, 400 status와 에러메시지 반환"() {
        given:
        postService.getPostList(_) >> { throw new IllegalArgumentException("요청한 페이지 수의 범위가 올바르지 않습니다.") }

        when:
        def result = mockMvc.perform(
                get(API).param("startDate", "2025-04-01T00:00:00")
                        .param("endDate", "2025-04-30T23:59:59")
                        .param("authorId", "1")
                        .param("sort", "NEWEST")
                        .param("page", "0")
                        .param("size", "9999"))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"요청한 페이지 수의 범위가 올바르지 않습니다.\"}"""
    }
}
