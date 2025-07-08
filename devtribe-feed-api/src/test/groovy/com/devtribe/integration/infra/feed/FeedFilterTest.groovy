package com.devtribe.integration.infra.feed

import com.devtribe.domain.post.dao.PostRepository
import com.devtribe.domain.post.dao.PostSearchRepository
import com.devtribe.domain.post.dto.PostSortCriteria
import com.devtribe.fixtures.post.domain.PostFixture
import com.devtribe.integration.AbstractIntegrationTest
import com.devtribe.integration.DataTestConfig
import com.devtribe.integration.DatabaseCleaner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Title

import java.time.LocalDateTime

import static com.devtribe.fixtures.post.dto.FeedFilterOptionFixture.createFeedFilterOption
import static com.devtribe.fixtures.post.dto.FeedSearchRequestFixture.createFeedSearchRequest

@Title("피드 검색 필터 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataTestConfig.class)
class FeedFilterTest extends AbstractIntegrationTest {

    @Autowired
    PostSearchRepository feedRepository

    @Autowired
    PostRepository postRepository

    @Autowired
    DatabaseCleaner databaseCleaner

    void cleanup() {
        databaseCleaner.clear()
    }

    void setup() {
        postRepository.saveAll(List.of(
                PostFixture.createPost(
                        title: "SQL 쿼리 시각화",
                        content: "SQL을 쉽게 이해하기",
                        userId: 1L,
                        createdAt: LocalDateTime.of(2025, 4, 1, 0, 0)),
                PostFixture.createPost(
                        title: "오픈소스 파이썬",
                        content: "파이썬 오픈소스 소개하는 글입니다",
                        userId: 2L,
                        createdAt: LocalDateTime.of(2025, 4, 2, 0, 0)),
                PostFixture.createPost(
                        title: "백엔드 개발 기술 향상",
                        content: "백엔드 성능 최적화를 위한 내용을 다룹니다",
                        userId: 3L,
                        createdAt: LocalDateTime.of(2025, 4, 3, 0, 0)),
                PostFixture.createPost(
                        title: "아키텍처 다이어그램",
                        content: "설계 구조 시각화를 위한 방법들을 소개합니다",
                        userId: 1L,
                        createdAt: LocalDateTime.of(2025, 4, 4, 0, 0)),
                PostFixture.createPost(
                        title: "백엔드 로드맵",
                        content: "백엔드 개발자가 되기 위한 로드맵에 대해서 소개합니다",
                        userId: 4L,
                        createdAt: LocalDateTime.of(2025, 4, 5, 0, 0))
        ))
    }

    def "키워드가 주어질때 키워드를 포함하는 title을 가진 피드 리스트를 반환한다."() {
        given:
        def filter = createFeedFilterOption(keyword: "성능")
        def request = createFeedSearchRequest(filter: filter, sort: PostSortCriteria.NEWEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 1
        result.data().collect { it.title } == ["백엔드 개발 기술 향상"]
    }

    def "키워드가 주어질때 키워드를 포함하는 content을 가진 피드 리스트를 반환한다"() {
        given:
        def filter = createFeedFilterOption(keyword: "성능")
        def request = createFeedSearchRequest(filter: filter, sort: PostSortCriteria.NEWEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 1
        result.data().collect { it.content } == ["백엔드 성능 최적화를 위한 내용을 다룹니다"]
    }

    def "시작일자와 종료일자가 주어질때 기간 안에 작성된 피드 리스트를 반환한다"() {
        given:
        def filter = createFeedFilterOption(
                startDate: LocalDateTime.of(2025, 4, 2, 0, 0),
                endDate: LocalDateTime.of(2025, 4, 4, 0, 0))
        def request = createFeedSearchRequest(filter: filter, sort: PostSortCriteria.NEWEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 3
        result.data().collect { it.createdAt } == [
                LocalDateTime.of(2025, 4, 4, 0, 0),
                LocalDateTime.of(2025, 4, 3, 0, 0),
                LocalDateTime.of(2025, 4, 2, 0, 0)
        ]
    }

    def "authorId가 주어질 때 작성자와 일치하는 피드 리스트를 반환한다"() {
        given:
        def filter = createFeedFilterOption(authorId: 1L)
        def request = createFeedSearchRequest(filter: filter, sort: PostSortCriteria.NEWEST)

        when:
        def result = feedRepository.findFeedsByFilterAndSortOption(request)

        then:
        result.data().size() == 2
        result.data().collect { it.userId } == [1L, 1L]
    }

}
