package com.devtribe.devtribe_feed_service.post.application.dtos;

public record PostVoteResponse(Long postId, Integer upvoteCount, Integer downvoteCount) {

}
