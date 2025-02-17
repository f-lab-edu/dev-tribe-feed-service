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
        def expectedUser = request.toUser()

        when:
        def user = userService.createUser(request)

        then:
        user == expectedUser

        and:
        1 * createUserRequestValidator.validateBiography(request.biography())
        1 * emailValidator.validateEmail(request.email())
        1 * passwordValidator.validatePassword(request.password())
        1 * userRepository.isEmailRegistered(request.email())
        1 * userRepository.isNicknameUsed(request.nickname())
        1 * userRepository.save(_) >> expectedUser
    }

    def "유효하지 않은 이메일로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        emailValidator.validateEmail(request.email()) >> { throw new IllegalArgumentException(message) }

        when:
        userService.createUser(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == message

        where:
        message << ["이메일은 비어 있거나 null일 수 없습니다.", "유효하지 않은 이메일 형식입니다.", "이메일에 허용되지 않는 문자가 포함되어 있습니다."]
    }

    def "유효하지 않은 비밀번호로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        passwordValidator.validatePassword(request.password()) >> { throw new IllegalArgumentException(message) }

        when:
        userService.createUser(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == message

        where:
        message << ["비밀번호는 빈 값일 수 없습니다.", "비밀번호의 길이가 유효하지 않습니다.", "비밀번호는 대문자, 소문자, 숫자 및 특수문자를 모두 포함해야 합니다."]
    }

    def "유효하지 않은 자기소개로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        createUserRequestValidator.validateBiography(request.biography()) >> { throw new IllegalArgumentException(message) }

        when:
        userService.createUser(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == message
    }

    def "이미 존재하는 이메일로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        userRepository.isEmailRegistered(request.email()) >> true

        when:
        userService.createUser(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "이미 가입된 이메일입니다."
    }

    def "이미 존재하는 닉네임으로 유저 생성 요청할 경우 회원가입에 실패한다."() {
        given:
        def request = Mock(CreateUserRequest)
        userRepository.isNicknameUsed(request.nickname()) >> true

        when:
        userService.createUser(request)

        then:
        def e = thrown(IllegalArgumentException)
        e.getMessage() == "이미 사용중인 닉네임입니다."
    }
}
