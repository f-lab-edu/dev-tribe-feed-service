package com.devtribe.domain.post.entity;

public enum FeedSortOption {
    // TODO: 정렬 옵션은 피드뿐만 아니라 다른 도메인에서도 활용 가능할 것으로 보임.
    //       추후 재사용할 경우, 공통 정렬 옵션으로 분리하는 방안 고민해보기
    NEWEST, OLDEST, UPVOTE, DOWNVOTE,
}