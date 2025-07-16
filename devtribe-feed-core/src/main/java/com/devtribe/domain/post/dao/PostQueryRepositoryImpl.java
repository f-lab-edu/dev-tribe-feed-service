package com.devtribe.domain.post.dao;

import static com.devtribe.domain.post.entity.QPost.post;

import com.devtribe.domain.post.dto.PostFilterCriteria;
import com.devtribe.domain.post.dto.PostQueryCriteria;
import com.devtribe.domain.post.dto.PostSortCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.Publication;
import com.devtribe.global.model.PageRequest;
import com.devtribe.global.model.PageResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;

public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final SortQueryFactory sortQueryFactory;
    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(SortQueryFactory sortQueryFactory, JPAQueryFactory queryFactory) {
        this.sortQueryFactory = sortQueryFactory;
        this.queryFactory = queryFactory;
    }

    @Override
    public PageResponse<Post> findPostsByCriteria(PostQueryCriteria postQueryCriteria) {
        PostFilterCriteria filter = postQueryCriteria.filterCriteria();
        PostSortCriteria sort = postQueryCriteria.sortCriteria();
        PageRequest pageRequest = postQueryCriteria.pageRequest();

        SortQuery sortQuery = sortQueryFactory.getSortQuery(sort);

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(
                eqPublic(),
                eqIsNotDeleted(),
                eqAuthor(filter.getAuthorId()),
                betweenDate(filter.getStartDate(), filter.getEndDate())
            )
            .orderBy(sortQuery.getOrderBy())
            .offset(pageRequest.getOffset())
            .limit(pageRequest.size())
            .fetch();

        return new PageResponse<>(queryResult, pageRequest.page());
    }

    private BooleanExpression eqPublic() {
        return post.publication.eq(Publication.PUBLIC);
    }

    private BooleanExpression eqIsNotDeleted() {
        return post.isDeleted.eq(false);
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
}
