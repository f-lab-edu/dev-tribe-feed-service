package com.devtribe.devtribe_feed_service.integration.infra.feed

import com.devtribe.devtribe_feed_service.integration.AbstractIntegrationTest
import com.devtribe.devtribe_feed_service.integration.DataTestConfig
import com.devtribe.devtribe_feed_service.integration.DatabaseCleaner
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption
import com.devtribe.devtribe_feed_service.test.utils.fixtures.post.PostFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

import java.time.LocalDateTime

import static com.devtribe.devtribe_feed_service.test.utils.fixtures.post.FeedSearchRequestFixture.createFeedSearchRequest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataTestConfig.class)
class FeedSortTest extends AbstractIntegrationTest {

    @Autowired
    FeedRepository feedRepository

    @Autowired
    PostRepository postRepository

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    def "정렬 옵션이 최신순일 경우 피드는 최신순으로 정렬되어야 한다."() {
        given:
        postRepository.saveAll(List.of(
                PostFixture.createPost(title: "title1", createdAt: LocalDateTime.now()),
                PostFixture.createPost(title: "title2", createdAt: LocalDateTime.now().plusMinutes(2)),
                PostFixture.createPost(title: "title3", createdAt: LocalDateTime.now().plusMinutes(3)),
                PostFixture.createPost(title: "title4", createdAt: LocalDateTime.now().plusMinutes(4)),
                PostFixture.createPost(title: "title5", createdAt: LocalDateTime.now().plusMinutes(5))
        ))
        def request = createFeedSearchRequest(sort: FeedSortOption.NEWEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 5
        result.data().collect { it.id } == [5L, 4L, 3L, 2L, 1L]
    }

    def "정렬 옵션이 오래된순일 경우 피드는 오래된순으로 정렬되어야 한다."() {
        given:
        postRepository.saveAll(List.of(
                PostFixture.createPost(title: "title1", createdAt: LocalDateTime.now()),
                PostFixture.createPost(title: "title2", createdAt: LocalDateTime.now().plusMinutes(2)),
                PostFixture.createPost(title: "title3", createdAt: LocalDateTime.now().plusMinutes(3)),
                PostFixture.createPost(title: "title4", createdAt: LocalDateTime.now().plusMinutes(4)),
                PostFixture.createPost(title: "title5", createdAt: LocalDateTime.now().plusMinutes(5))
        ))
        def request = createFeedSearchRequest(sort: FeedSortOption.OLDEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 5
        result.data().collect { it.id } == [1L, 2L, 3L, 4L, 5L]
    }

    def "정렬 옵션이 추천순일 경우 피드는 추천순으로 정렬되어야 한다."() {
        given:
        postRepository.saveAll(List.of(
                PostFixture.createPost(title: "title1", upvoteCount: 10),
                PostFixture.createPost(title: "title2", upvoteCount: 50),
                PostFixture.createPost(title: "title3", upvoteCount: 13),
                PostFixture.createPost(title: "title4", upvoteCount: 41),
                PostFixture.createPost(title: "title5", upvoteCount: 19)
        ))
        def request = createFeedSearchRequest(sort: FeedSortOption.UPVOTE)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 5
        result.data().collect { it.upvoteCount } == [50, 41, 19, 13, 10]
    }

    def "정렬 옵션이 비추천순일 경우 피드는 비추천순으로 정렬되어야 한다."() {
        given:
        postRepository.saveAll(List.of(
                PostFixture.createPost(title: "title1", downvoteCount: 10),
                PostFixture.createPost(title: "title2", downvoteCount: 50),
                PostFixture.createPost(title: "title3", downvoteCount: 13),
                PostFixture.createPost(title: "title4", downvoteCount: 41),
                PostFixture.createPost(title: "title5", downvoteCount: 19)
        ))
        def request = createFeedSearchRequest(sort: FeedSortOption.DOWNVOTE)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 5
        result.data().collect { it.downvoteCount } == [50, 41, 19, 13, 10]
    }

}
