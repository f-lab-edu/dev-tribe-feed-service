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
import com.querydsl.core.types.dsl.BooleanExpression;
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
                containKeyword(filter.getKeyword()),
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
     * TODO: containsIgnoreCase 사용으로 대량 데이터에서 성능 저하 가능.
     * <p>현재 방식은 인덱스를 타지 않아 못해 데이터 양이 많아질 경우, 성능 저하가 발생 가능.
     * 추후 전체 텍스트 검색(Full Text Search)이나 외부검색엔진(Elasticsearch) 도입 고려 필요.</p>
     */
    private BooleanExpression containKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        return post.title.containsIgnoreCase(keyword).or(post.content.containsIgnoreCase(keyword));
    }
}
