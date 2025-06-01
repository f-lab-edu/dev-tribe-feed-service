package com.devtribe.domain.post.dao;

import static com.devtribe.domain.post.entity.QPost.post;

import com.devtribe.domain.post.entity.FeedSortOption;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SortQueryFactory {

    private final Map<FeedSortOption, SortQuery> sortQueryStrategyMap = Map.of(
        FeedSortOption.NEWEST, post.id::desc,
        FeedSortOption.OLDEST, post.id::asc,
        FeedSortOption.UPVOTE, post.upvoteCount::desc,
        FeedSortOption.DOWNVOTE, post.downvoteCount::desc
    );

    public SortQuery getSortQuery(FeedSortOption feedSortOption) {
        return sortQueryStrategyMap.get(feedSortOption);
    }

}
