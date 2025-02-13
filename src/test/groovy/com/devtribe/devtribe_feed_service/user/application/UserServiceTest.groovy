package com.devtribe.devtribe_feed_service.user.application

import com.devtribe.devtribe_feed_service.user.application.dtos.CreateUserRequest
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository
import com.devtribe.devtribe_feed_service.user.application.validators.CreateUserRequestValidator
import com.devtribe.devtribe_feed_service.user.application.validators.EmailValidator
import com.devtribe.devtribe_feed_service.user.application.validators.PasswordValidator
import spock.lang.Specification
import spock.lang.Subject

class UserServiceTest extends Specification {

    def userRepository = Mock(UserRepository)
    def emailValidator = Mock(EmailValidator)
    def passwordValidator = Mock(PasswordValidator)
    def createUserRequestValidator = Mock(CreateUserRequestValidator)

    @Subject
    UserService userService = new UserService(userRepository, emailValidator, passwordValidator, createUserRequestValidator)

    def "유효한 유저 생성 요청이 주어지면 회원가입이 성공한다."() {
        given:
        def request = Mock(CreateUserRequest)

        when:
        userService.createUser(request)

        then:
        1 * createUserRequestValidator.validateBiography(request.biography())
        1 * emailValidator.validateEmail(request.email())
        1 * passwordValidator.validatePassword(request.password())
        1 * userRepository.isEmailRegistered(request.email())
        1 * userRepository.isNicknameUsed(request.nickname())
        1 * userRepository.save(request.toUser())
    }

    def "유효하지 않은 이메일로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        emailValidator.validateEmail(request.email()) >> { throw new IllegalArgumentException() }

        when:
        userService.createUser(request)

        then:
        thrown(IllegalArgumentException)
    }

    def "유효하지 않은 비밀번호로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        passwordValidator.validatePassword(request.password()) >> { throw new IllegalArgumentException() }

        when:
        userService.createUser(request)

        then:
        thrown(IllegalArgumentException)
    }

    def "유효하지 않은 자기소개로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        createUserRequestValidator.validateBiography(request.biography()) >> { throw new IllegalArgumentException() }

        when:
        userService.createUser(request)

        then:
        thrown(IllegalArgumentException)
    }

    def "이미 존재하는 이메일로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        userRepository.isEmailRegistered(request.biography()) >> true

        when:
        userService.createUser(request)

        then:
        thrown(IllegalArgumentException)
    }

    def "이미 존재하는 닉네임으로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        userRepository.isNicknameUsed(request.nickname()) >> true

        when:
        userService.createUser(request)

        then:
        thrown(IllegalArgumentException)
    }
}
