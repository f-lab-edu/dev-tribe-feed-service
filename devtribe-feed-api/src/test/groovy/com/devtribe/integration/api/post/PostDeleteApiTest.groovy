package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.PostController
import com.devtribe.domain.post.application.PostService
import com.devtribe.integration.config.NoSecurityWebMvcTest
import com.devtribe.integration.config.TestSecurityConfig
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@Title(value = "게시글 삭제 API 테스트")
@Import(TestSecurityConfig.class)
@NoSecurityWebMvcTest(controllers = PostController.class)
class PostDeleteApiTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    PostService postService = Mock(PostService)

    def "게시물 삭제 성공 - 204 status 반환"() {
        given:
        def postId = 1L

        when:
        def result = mockMvc.perform(
                delete("/api/v1/posts/{id}", postId))
                .andDo(print())
                .andReturn()

        then:
        result.response.status == HttpStatus.NO_CONTENT.value()
    }

    def "게시물 삭제 실패 - 존재하지 않는 게시물, 400 status와 예외메시지를 반환한다."() {
        given:
        def nonExistingPostId = 999L
        postService.deletePost(nonExistingPostId) >> { throw new IllegalArgumentException("존재하지 않는 게시물입니다.") }

        when:
        def result = mockMvc.perform(
                delete("/api/v1/posts/{id}", nonExistingPostId))
                .andDo(print())
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        final JsonNode responseBody = objectMapper.readTree(content)

        result.response.status == HttpStatus.BAD_REQUEST.value()
        responseBody.get("errorMessage").asText() == "존재하지 않는 게시물입니다."
    }

}
