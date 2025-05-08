package com.devtribe.devtribe_feed_service.integration

import com.devtribe.devtribe_feed_service.post.application.VoteService
import com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository
import com.devtribe.devtribe_feed_service.post.repository.jpa.JpaVoteRepository
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.PostFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.AuditorAware

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostVoteConcurrencyTest extends AbstractIntegrationTest {

    @Autowired
    VoteService voteService

    @Autowired
    PostRepository postRepository

    @Autowired
    JpaVoteRepository voteRepository

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        AuditorAware<Long> auditorProvider() {
            return () -> Optional.of(1L); // 테스트용으로 1L을 반환
        }
    }

    def "여러 사용자가 동일 게시물에 대해 추천할 경우 스레드만큼 추천수가 증가해야한다."(){
        given:
        def threadCount = 20
        def executor = Executors.newFixedThreadPool(threadCount)
        def latch = new CountDownLatch(threadCount)
        def post = postRepository.save(PostFixture.createPost(createdBy: 1L))

        when:
        threadCount.times {
            def userId = it + 1L
            executor.submit {
                try {
                    def request = VoteRequest.upvotePostRequest(userId, post.id)
                    voteService.vote(request)
                } catch (Exception e) {
                    e.printStackTrace()
                    throw e
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        then:
        def updated = postRepository.findById(post.id).get()
        println "최종 upvote 수: ${updated.upvoteCount}"
        println "최종 downvote 수: ${updated.downvoteCount}"
        updated.upvoteCount == threadCount
        updated.downvoteCount == 0

        def allVote = voteRepository.findAll()
        allVote.size() == threadCount
    }

    def "동일한 유저가 동일 게시물에 동시에 추천할 경우 추천수는 1만 증가해야한다."(){
        given:
        def threadCount = 20
        def executor = Executors.newFixedThreadPool(threadCount)
        def latch = new CountDownLatch(threadCount)
        def post = postRepository.save(PostFixture.createPost(createdBy: 1L))

        when:
        threadCount.times {
            def userId = 1L
            executor.submit {
                try {
                    def request = VoteRequest.upvotePostRequest(userId, post.id)
                    voteService.vote(request)
                } catch (Exception e) {
                    e.printStackTrace()
                    throw e
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        then:
        def updated = postRepository.findById(post.id).get()
        println "최종 upvote 수: ${updated.upvoteCount}"
        println "최종 downvote 수: ${updated.downvoteCount}"
        updated.upvoteCount == 1
        updated.downvoteCount == 0

        def allVote = voteRepository.findAll()
        allVote.size() == 1
    }
}
