package com.devtribe.integration.api.post

import com.devtribe.domain.post.api.PostController
import com.devtribe.domain.post.application.PostService
import com.devtribe.domain.post.application.dtos.CreatePostResponse
import com.devtribe.domain.post.application.dtos.UpdatePostRequest
import com.devtribe.domain.post.application.dtos.UpdatePostResponse
import com.devtribe.domain.post.entity.Publication
import com.devtribe.fixtures.post.dto.CreatePostRequestFixture
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@Title(value = "게시글 수정 API 테스트")
@Import(TestSecurityConfig.class)
@NoSecurityWebMvcTest(controllers = PostController.class)
class PostUpdateApiTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    PostService postService = Mock(PostService)

    def "게시물 수정 성공 - 200 status와 수정 결과를 반환한다."() {
        given:
        def postId = 1L
        def updateRequest = new UpdatePostRequest(
                1L,
                "변경 제목",
                "변경 본문",
                "https://example.com/change-thumbnail.jpg",
                Publication.PRIVATE,
                List.of(1L)
        )
        def updateResponse = new UpdatePostResponse(
                "변경 제목",
                "변경 본문",
                "https://example.com/change-thumbnail.jpg",
                Publication.PRIVATE
        )
        postService.updatePost(postId, updateRequest, _) >> updateResponse

        when:
        def result = mockMvc.perform(
                put("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        final JsonNode responseBody = objectMapper.readTree(content)

        result.response.status == HttpStatus.OK.value()
        responseBody.get("title").asText() == updateRequest.title()
        responseBody.get("content").asText() == updateRequest.content()
        responseBody.get("thumbnail").asText() == updateRequest.thumbnail()
        responseBody.get("publication").asText() == updateRequest.publication().name()

    }

    def "게시물 수정 실패 - 유효하지 않은 요청, 400 status와 예외메시지를 반환한다."() {
        given:
        def updateRequest = new UpdatePostRequest(
                "변경 제목",
                "변경 본문",
                "https://example.com/change-thumbnail.jpg",
                Publication.PRIVATE,
                List.of(1L)
        )
        postService.updatePost(postId, updateRequest, _) >> { throw new IllegalArgumentException(exceptionMessage) }

        when:
        def result = mockMvc.perform(
                put("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andReturn()

        then:
        def content = result.response.getContentAsString()
        final JsonNode responseBody = objectMapper.readTree(content)

        result.response.status == exceptStatusCode
        responseBody.get("errorMessage").asText() == exceptionMessage

        where:
        postId | userId | exceptStatusCode               | exceptionMessage
        999L   | 1L     | HttpStatus.BAD_REQUEST.value() | "존재하지 않는 게시물입니다."
        1L     | 999L   | HttpStatus.BAD_REQUEST.value() | "존재하지 않는 유저입니다."
        1L     | 2L     | HttpStatus.BAD_REQUEST.value() | "게시물 작성자가 아닙니다."
    }
}
