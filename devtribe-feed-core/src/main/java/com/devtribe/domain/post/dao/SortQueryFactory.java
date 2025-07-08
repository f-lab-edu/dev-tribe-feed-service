package com.devtribe.domain.post.dao;

import static com.devtribe.domain.post.entity.QPost.post;

import com.devtribe.domain.post.dto.PostSortCriteria;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SortQueryFactory {

    private final Map<PostSortCriteria, SortQuery> sortQueryStrategyMap = Map.of(
        PostSortCriteria.NEWEST, post.id::desc,
        PostSortCriteria.OLDEST, post.id::asc,
        PostSortCriteria.UPVOTE, post.upvoteCount::desc,
        PostSortCriteria.DOWNVOTE, post.downvoteCount::desc
    );

    public SortQuery getSortQuery(PostSortCriteria postSortCriteria) {
        return sortQueryStrategyMap.get(postSortCriteria);
    }

}
