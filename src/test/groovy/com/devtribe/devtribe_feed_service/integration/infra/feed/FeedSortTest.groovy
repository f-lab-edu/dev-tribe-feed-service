package com.devtribe.devtribe_feed_service.integration.infra.feed

import com.devtribe.devtribe_feed_service.global.common.CursorPagination
import com.devtribe.devtribe_feed_service.integration.AbstractIntegrationTest
import com.devtribe.devtribe_feed_service.integration.DataTestConfig
import com.devtribe.devtribe_feed_service.integration.DatabaseCleaner
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository
import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption
import com.devtribe.devtribe_feed_service.post.domain.Post
import com.devtribe.devtribe_feed_service.post.domain.Publication
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

    def "피드가 최신순으로 정렬되어 반환되어야 한다"() {
        given:
        postRepository.save(
                new Post(
                        userId: 1L,
                        title: "title",
                        content: "content",
                        thumbnail: "thumbnail",
                        publication: Publication.PUBLIC,
                        upvoteCount: 1,
                        downvoteCount: 3,
                        isDeleted: false,
                        createdAt: LocalDateTime.now().minusMinutes(1),
                        createdBy: 1L
                )
        )

        def pagination = new CursorPagination(null, 5)
        def newest = FeedSortOption.BY_NEWEST

        when:
        def result = feedRepository.findAllBySortOption(pagination, newest)

        then:
        result.totalCount() == 1
    }
}
