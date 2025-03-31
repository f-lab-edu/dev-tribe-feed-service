package com.devtribe.devtribe_feed_service.integration.infra.user

import com.devtribe.devtribe_feed_service.integration.AbstractIntegrationTest
import com.devtribe.devtribe_feed_service.integration.DataTestConfig
import com.devtribe.devtribe_feed_service.integration.DatabaseCleaner
import com.devtribe.devtribe_feed_service.test.utils.fixtures.UserFixture
import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataTestConfig.class)
class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository

    @Autowired
    EntityManager entityManager

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    def "유저를 저장하면 저장된 유저가 반환된다"() {
        given:
        def user = UserFixture.createUser()

        when:
        def savedUser = userRepository.save(user)

        then:
        savedUser != null
        savedUser.id == 1L
    }

    def "등록된 이메일이라면 true를 반환한다"(){
        given:
        def existingEmail = "existing@email.com"
        def user = UserFixture.createUser(email: existingEmail)
        userRepository.save(user)

        expect:
        userRepository.isEmailRegistered(existingEmail)
    }

    def "등록되지 이메일이라면 false를 반환한다"(){
        given:
        def nonExistingEmail = "nonexisting@email.com"

        expect:
        !userRepository.isEmailRegistered(nonExistingEmail)
    }

    def "사용중인 닉네임이라면 true를 반환한다"(){
        given:
        def usedNickname = "usedNickname"
        def user = UserFixture.createUser(nickname: usedNickname)
        userRepository.save(user)

        expect:
        userRepository.isNicknameUsed(usedNickname)
    }

    def "사용중이지 않은 닉네임이라면 false 반환한다"(){
        given:
        def notUsedNickname = "notUsedNickname"

        expect:
        !userRepository.isNicknameUsed(notUsedNickname)
    }

    def "존재하는 id라면 유저를 반환한다"(){
        given:
        def user = UserFixture.createUser()
        def savedUser = userRepository.save(user)
        entityManager.flush()
        entityManager.clear()

        when:
        def findUser = userRepository.findById(savedUser.getId())

        then:
        findUser.isPresent()
        findUser.get().id == 1L
    }

    def "존재하지 않는 id라면 empty를 반환한다"(){
        given:
        def nonExistingId = 999L

        when:
        def findUser = userRepository.findById(nonExistingId)

        then:
        findUser.isEmpty()
    }

    def "존재하는 이메일이라면 유저를 반환한다"(){
        given:
        def existingEmail = "existingEmail"
        def user = UserFixture.createUser(email: existingEmail)
        userRepository.save(user)
        entityManager.flush()
        entityManager.clear()

        when:
        def findUser = userRepository.findByEmail(existingEmail)

        then:
        findUser.isPresent()
        findUser.get().email == existingEmail
    }

    def "존재하지 않는 이메일이라면 empty를 반환한다"(){
        given:
        def nonExistingEmail = "nonExistingEmail"

        when:
        def findUser = userRepository.findByEmail(nonExistingEmail)

        then:
        findUser.isEmpty()
    }

}
