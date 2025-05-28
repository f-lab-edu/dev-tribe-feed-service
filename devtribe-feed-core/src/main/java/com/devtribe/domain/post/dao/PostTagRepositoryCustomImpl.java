package com.devtribe.domain.post.dao;

import com.devtribe.domain.post.entity.QPostTag;
import com.devtribe.domain.tag.entity.QTag;
import com.devtribe.domain.tag.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostTagRepositoryCustomImpl implements PostTagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostTagRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Tag> findAllTagByPostId(Long postId) {
        return queryFactory
            .select(QTag.tag)
            .from(QPostTag.postTag)
            .join(QTag.tag)
            .on(QPostTag.postTag.tagId.eq(QTag.tag.id))
            .where(QPostTag.postTag.postId.eq(postId))
            .fetch();
    }
}
