package com.devtribe.domain.post.dao;


import static com.devtribe.domain.post.entity.QPost.post;

import com.devtribe.domain.post.dto.PostSearchCriteria;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.post.entity.Publication;
import com.devtribe.global.model.PageRequest;
import com.devtribe.global.model.PageResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostSearchRepositoryImpl implements PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    public PostSearchRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public PageResponse<Post> searchPostByKeyword(PostSearchCriteria criteria) {
        String keyword = criteria.keyword();
        PageRequest pageRequest = criteria.pageRequest();

        List<Post> queryResult = queryFactory
            .selectFrom(post)
            .where(
                eqPublic(),
                eqIsNotDeleted(),
                searchKeyword(keyword)
            )
            .offset(pageRequest.getOffset())
            .limit(pageRequest.size())
            .fetch();

        return new PageResponse<>(queryResult, pageRequest.page());
    }

    private BooleanExpression eqIsNotDeleted() {
        return post.isDeleted.eq(false);
    }

    private BooleanExpression eqPublic() {
        return post.publication.eq(Publication.PUBLIC);
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
