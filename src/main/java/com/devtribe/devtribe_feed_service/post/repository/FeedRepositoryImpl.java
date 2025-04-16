package com.devtribe.devtribe_feed_service.post.repository;

import static com.devtribe.devtribe_feed_service.post.domain.QPost.post;

import com.devtribe.devtribe_feed_service.global.common.PageResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.FeedSearchRequest;
import com.devtribe.devtribe_feed_service.post.application.interfaces.FeedRepository;
import com.devtribe.devtribe_feed_service.post.domain.FeedFilterOption;
import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.domain.Publication;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQuery;
import com.devtribe.devtribe_feed_service.post.repository.query.SortQueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    private final SortQueryFactory sortQueryFactory;
    private final JPAQueryFactory queryFactory;

    public FeedRepositoryImpl(SortQueryFactory sortQueryFactory, JPAQueryFactory queryFactory) {
        this.sortQueryFactory = sortQueryFactory;
        this.queryFactory = queryFactory;
    }

    @Override
    public PageResponse<Post> findFeedsByFilterAndSortOption(FeedSearchRequest request) {

        FeedFilterOption filter = request.feedFilterOption();
        FeedSortOption sort = request.feedSortOption();
        SortQuery sortQuery = sortQueryFactory.getSortQuery(sort);

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(
                searchKeyword(filter.getKeyword()),
                betweenDate(filter.getStartDate(), filter.getEndDate()),
                eqAuthor(filter.getAuthorId()),
                eqPublic()
            )
            .orderBy(sortQuery.getOrderBy())
            .offset(request.offset())
            .limit(request.size())
            .fetch();

        return new PageResponse<>(queryResult, request.offset());
    }

    private BooleanExpression eqPublic() {
        return post.publication.eq(Publication.PUBLIC);
    }

    private BooleanExpression eqAuthor(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return post.userId.eq(authorId);
    }

    private BooleanExpression betweenDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        return post.createdAt.between(startDate, endDate);
    }

    /**
     * TODO: Full-Text 검색방식으로 변경
     * <p> 추후 외부검색엔진(Elasticsearch) 도입 고려 필요.</p>
     */
    private Predicate searchKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }

        return Expressions.booleanTemplate(
            "function('match_against', {0}, {1}, {2}) > 0",
            post.title,
            post.content,
            keyword
        );
    }
}
