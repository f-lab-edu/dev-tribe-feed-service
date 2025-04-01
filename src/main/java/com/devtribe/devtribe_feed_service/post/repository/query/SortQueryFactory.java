package com.devtribe.devtribe_feed_service.post.repository.query;

import com.devtribe.devtribe_feed_service.post.domain.FeedSortOption;
import com.devtribe.devtribe_feed_service.post.repository.query.sortquery.DownvoteSort;
import com.devtribe.devtribe_feed_service.post.repository.query.sortquery.NewestSort;
import com.devtribe.devtribe_feed_service.post.repository.query.sortquery.OldestSort;
import com.devtribe.devtribe_feed_service.post.repository.query.sortquery.UpvoteSort;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SortQueryFactory {

    private final Map<FeedSortOption, SortQuery> sortQueryStrategyMap = Map.of(
        FeedSortOption.NEWEST, new NewestSort(),
        FeedSortOption.OLDEST, new OldestSort(),
        FeedSortOption.UPVOTE, new UpvoteSort(),
        FeedSortOption.DOWNVOTE, new DownvoteSort()
    );

    public SortQuery getSortQuery(FeedSortOption feedSortOption) {
        return sortQueryStrategyMap.get(feedSortOption);
    }

}
