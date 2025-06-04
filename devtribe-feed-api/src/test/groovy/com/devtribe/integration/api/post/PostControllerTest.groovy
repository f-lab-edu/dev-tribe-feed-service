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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@Title(value = "게시글 컨트롤러 테스트")
@Import(TestSecurityConfig.class)
@NoSecurityWebMvcTest(controllers = PostController.class)
class PostControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc
    @Autowired
    ObjectMapper objectMapper
    @SpringBean
    PostService postService = Mock(PostService)

    def "게시물 생성 성공 - 200 status와 게시물 id를 반환"() {
        given:
        def createRequest = CreatePostRequestFixture.createPostRequest(
                title: "새로운 Post 제목",
                content: "새로운 내용",
                thumbnail: "https://example.com/thumbnail.jpg"
        )
        def response = new CreatePostResponse(1L)
        postService.createPost(createRequest, _) >> response

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
        def createRequest = CreatePostRequestFixture.createPostRequest(
                title: "새로운 Post 제목",
                content: "새로운 내용",
                thumbnail: "https://example.com/thumbnail.jpg"
        )
        postService.createPost(createRequest, _) >> { throw new IllegalArgumentException("존재하지 않는 유저입니다.") }

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

    def "게시물 수정 성공 - 200 status와 수정 결과를 반환한다."() {
        given:
        def postId = 1L
        def updateRequest = new UpdatePostRequest(
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
