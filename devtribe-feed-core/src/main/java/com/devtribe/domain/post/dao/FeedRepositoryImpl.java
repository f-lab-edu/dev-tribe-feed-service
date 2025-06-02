package com.devtribe.domain.post.dao;


import static com.devtribe.domain.post.entity.QPost.post;

import com.devtribe.domain.post.entity.FeedFilterOption;
import com.devtribe.domain.post.entity.FeedSortOption;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.Publication;
import com.devtribe.global.model.FeedSearchRequest;
import com.devtribe.global.model.PageResponse;
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
