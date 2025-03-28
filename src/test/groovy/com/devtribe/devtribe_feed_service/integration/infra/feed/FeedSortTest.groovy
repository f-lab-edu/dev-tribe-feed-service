package com.devtribe.devtribe_feed_service.integration.infra.feed

import com.devtribe.devtribe_feed_service.global.common.CursorPagination
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
        postRepository.save(PostFixture.createPost(title: "title1", createdAt: LocalDateTime.now()))
        postRepository.save(PostFixture.createPost(title: "title2", createdAt: LocalDateTime.now().plusMinutes(2)))
        postRepository.save(PostFixture.createPost(title: "title3", createdAt: LocalDateTime.now().plusMinutes(3)))
        postRepository.save(PostFixture.createPost(title: "title4", createdAt: LocalDateTime.now().plusMinutes(4)))
        postRepository.save(PostFixture.createPost(title: "title5", createdAt: LocalDateTime.now().plusMinutes(5)))

        def expectedPostId = List.of(5, 4, 3, 2, 1)

        def pagination = new CursorPagination(null, null)
        def newest = FeedSortOption.BY_NEWEST

        when:
        def result = feedRepository.findAllBySortOption(pagination, newest)

        then:
        result.totalCount() == expectedPostId.size()
        result.data().eachWithIndex { post, index ->
            assert post.id == expectedPostId[index]
        }
    }

    def "정렬 옵션이 오래된순일 경우 피드는 오래된순으로 정렬되어야 한다."() {
        given:
        postRepository.save(PostFixture.createPost(title: "title1", createdAt: LocalDateTime.now()))
        postRepository.save(PostFixture.createPost(title: "title2", createdAt: LocalDateTime.now().plusMinutes(2)))
        postRepository.save(PostFixture.createPost(title: "title3", createdAt: LocalDateTime.now().plusMinutes(3)))
        postRepository.save(PostFixture.createPost(title: "title4", createdAt: LocalDateTime.now().plusMinutes(4)))
        postRepository.save(PostFixture.createPost(title: "title5", createdAt: LocalDateTime.now().plusMinutes(5)))

        def expectedPostId = List.of(1, 2, 3, 4, 5)

        def pagination = new CursorPagination(null, null)
        def oldest = FeedSortOption.BY_DOWNVOTE

        when:
        def result = feedRepository.findAllBySortOption(pagination, oldest)

        then:
        result.totalCount() == expectedPostId.size()
        result.data().eachWithIndex { post, index ->
            assert post.id == expectedPostId[index]
        }
    }


    def "정렬 옵션이 추천순일 경우 피드는 오래된순으로 정렬되어야 한다."() {
        given:
        postRepository.save(PostFixture.createPost(title: "title1", upvoteCount: 10))
        postRepository.save(PostFixture.createPost(title: "title2", upvoteCount: 50))
        postRepository.save(PostFixture.createPost(title: "title3", upvoteCount: 13))
        postRepository.save(PostFixture.createPost(title: "title4", upvoteCount: 41))
        postRepository.save(PostFixture.createPost(title: "title5", upvoteCount: 19))

        def expectedUpvoteCount = List.of(50, 41, 19, 13, 10)

        def pagination = new CursorPagination(null, null)
        def upvote = FeedSortOption.BY_UPVOTE

        when:
        def result = feedRepository.findAllBySortOption(pagination, upvote)

        then:
        result.totalCount() == 5
        result.data().eachWithIndex { post, index ->
            assert post.upvoteCount == expectedUpvoteCount[index]
        }
    }

    def "정렬 옵션이 비추천순일 경우 피드는 오래된순으로 정렬되어야 한다."() {
        given:
        postRepository.save(PostFixture.createPost(title: "title1", downvoteCount: 10))
        postRepository.save(PostFixture.createPost(title: "title2", downvoteCount: 50))
        postRepository.save(PostFixture.createPost(title: "title3", downvoteCount: 13))
        postRepository.save(PostFixture.createPost(title: "title4", downvoteCount: 41))
        postRepository.save(PostFixture.createPost(title: "title5", downvoteCount: 19))

        def expectedDownVoteCount = List.of(50, 41, 19, 13, 10)

        def pagination = new CursorPagination(null, null)
        def downvote = FeedSortOption.BY_DOWNVOTE

        when:
        def result = feedRepository.findAllBySortOption(pagination, downvote)

        then:
        result.totalCount() == 5
        result.data().eachWithIndex { post, index ->
            assert post.downvoteCount == expectedDownVoteCount[index]
        }
    }

}
