package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.PostSearchController
import com.devtribe.domain.post.application.PostSearchService
import com.devtribe.integration.config.NoSecurityWebMvcTest
import com.devtribe.integration.config.TestSecurityConfig
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Title(value = "게시글 검색 API 테스트")
@NoSecurityWebMvcTest(controllers = PostSearchController.class)
@Import(TestSecurityConfig.class)
class PostSearchApiTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    PostSearchService postSearchService = Mock(PostSearchService)

    @Shared
    final String API = "/api/v1/posts/search"

    def "게시글 검색 성공 - 200 status 반환"() {
        when:
        def result = mockMvc.perform(
                get(API).param("keyword", "스프링")
                        .param("page", "0")
                        .param("size", "10"))
                .andReturn()

        then:
        result.response.status == HttpStatus.OK.value()
    }

    def "게시글 검색 실패 - 필수 파라미터(키워드) 누락, 400 status와 에러메시지 반환"() {
        given:
        postSearchService.searchPostByKeyword(_) >> { throw new IllegalArgumentException("키워드는 필수값입니다.") }

        when:
        def result = mockMvc.perform(
                get(API).param("page", "0")
                        .param("size", "10"))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"키워드는 필수값입니다.\"}"""
    }

    def "게시글 검색 실패 - 필수 파라미터(키워드) 빈 값, 400 status와 에러메시지 반환"() {
        given:
        postSearchService.searchPostByKeyword(_) >> { throw new IllegalArgumentException("키워드는 필수값입니다.") }

        when:
        def result = mockMvc.perform(
                get(API).param("keyword", "")
                        .param("page", "0")
                        .param("size", "10"))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"키워드는 필수값입니다.\"}"""
    }

    def "게시글 검색 실패 - 페이지 크기 초과, 400 status와 에러메시지 반환"() {
        given:
        postSearchService.searchPostByKeyword(_) >> { throw new IllegalArgumentException("요청한 페이지 수의 범위가 올바르지 않습니다.") }

        when:
        def result = mockMvc.perform(
                get(API).param("keyword", "스프링")
                        .param("page", "0")
                        .param("size", "9999"))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{\"errorMessage":"요청한 페이지 수의 범위가 올바르지 않습니다.\"}"""
    }
}
