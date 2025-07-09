package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.PostController
import com.devtribe.domain.post.application.PostService
import com.devtribe.domain.post.application.dtos.CreatePostRequest
import com.devtribe.domain.post.application.dtos.CreatePostResponse
import com.devtribe.integration.config.NoSecurityWebMvcTest
import com.devtribe.integration.config.TestSecurityConfig
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Title

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@Title(value = "게시글 생성 API 테스트")
@Import(TestSecurityConfig.class)
@NoSecurityWebMvcTest(controllers = PostController.class)
class PostCreateApiTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    PostService postService = Mock(PostService)

    def "게시물 생성 성공 - 200 status와 게시물 id를 반환"() {
        given:
        def createRequest = new CreatePostRequest(
                "새로운 Post 제목",
                "새로운 내용",
                1L,
                "https://example.com/thumbnail.jpg"
        )
        def response = new CreatePostResponse(1L)
        postService.createPost(createRequest) >> response

        when:
        def result = mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        final JsonNode responseBody = objectMapper.readTree(content)

        result.response.status == HttpStatus.OK.value()
        responseBody.get("id").asLong() == response.id()
    }

    def "게시물 생성 실패 - 존재하지 않는 유저, 400 status와 예외메시지를 반환한다."() {
        given:
        def createRequest = new CreatePostRequest(
                "새로운 Post 제목",
                "새로운 내용",
                999L,
                "https://example.com/thumbnail.jpg"
        )
        postService.createPost(createRequest) >> { throw new IllegalArgumentException("존재하지 않는 유저입니다.") }

        when:
        def result = mockMvc.perform(
                post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
    }
}
