package com.devtribe.devtribe_feed_service.integration.api.user

import com.devtribe.devtribe_feed_service.test.config.NoSecurityWebMvcTest
import com.devtribe.devtribe_feed_service.test.config.TestSecurityConfig
import com.devtribe.devtribe_feed_service.user.api.UserController
import com.devtribe.devtribe_feed_service.user.application.UserService
import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Title

import static com.devtribe.devtribe_feed_service.test.utils.fixtures.CreateUserRequestFixtures.createRequiredUserRequest
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@Title(value = "유저 컨트롤러 테스트")
@NoSecurityWebMvcTest(controllers = UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    UserService userService = Mock()

    def "유저 회원가입 성공 - 200 status와 반환값"() {
        given:
        def requestBody = createRequiredUserRequest("existing@gmail.com", "password", "codongmin")
        def response = new CreateUserResponse(1L, "codongmin")
        userService.createUser(_) >> response

        when:
        def result = mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andReturn()

        then:
        result.response.status == HttpStatus.CREATED.value()
        result.response.getContentAsString() == """{"userId":1,"nickname":"codongmin"}"""
    }

    def "유저 회원가입 실패 - 400 status 반환"() {
        given:
        def requestBody = createRequiredUserRequest(email, "password", nickname)
        userService.createUser(_) >> { throw new IllegalArgumentException(exceptionMessage) }

        when:
        def result = mockMvc.perform(post("/api/v1/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andReturn()

        then:
        result.response.status == HttpStatus.BAD_REQUEST.value()
        result.response.getContentAsString() == """{"errorMessage":"$exceptionMessage"}"""

        where:
        email                     | nickname           | exceptionMessage
        "existingEmail@gmail.com" | "exampleNickname"  | "이미 가입된 이메일입니다."
        "example@gmail.com"       | "existingNickname" | "이미 사용중인 닉네임입니다."

    }

}
