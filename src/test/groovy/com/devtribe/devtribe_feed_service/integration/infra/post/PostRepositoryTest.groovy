package com.devtribe.devtribe_feed_service.integration.infra.post

import com.devtribe.devtribe_feed_service.integration.AbstractIntegrationTest
import com.devtribe.devtribe_feed_service.integration.DataTestConfig
import com.devtribe.devtribe_feed_service.integration.DatabaseCleaner
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.PostFixture
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataTestConfig.class)
class PostRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PostRepository postRepository

    @Autowired
    EntityManager entityManager

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    def "게시글을 저장하면 저장된 게시글이 반환된다"() {
        given:
        def post = PostFixture.createPost()

        when:
        def savedPost = postRepository.save(post)

        then:
        savedPost != null
        savedPost.getId() == 1L
    }

    def "존재하는 게시글이라면 게시글을 반환한다"(){
        given:
        def post = PostFixture.createPost()
        def savedPost = postRepository.save(post)
        entityManager.flush()
        entityManager.clear()

        when:
        def result = postRepository.findById(savedPost.getId())

        then:
        result.isPresent()
        result.get().id == 1L
    }

    def "존재하지 않는 게시글이라면 empty를 반환한다"(){
        given:
        def nonExistingId = 999L

        when:
        def result = postRepository.findById(nonExistingId)

        then:
        result.isEmpty()
    }
}
