package com.devtribe.devtribe_feed_service.post.application;

import static java.lang.Integer.parseInt;

import com.devtribe.devtribe_feed_service.post.application.dtos.PostVoteResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    public static final String POST_VOTES_KEY = "post:%d:votes";
    public static final String UPVOTE_COUNT_KEY = "post:upvoteCount";
    public static final String DOWNVOTE_COUNT_KEY = "post:downvoteCount";

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisScript<List> postVoteScript;
    private final RedisScript<List> postUnvoteScript;
    private final RedisScript<List> postVoteCountScript;

    public VoteService(
        RedisTemplate<String, String> redisTemplate,
        RedisScript<List> postVoteScript,
        RedisScript<List> postUnvoteScript,
        RedisScript<List> postVoteCountScript
    ) {
        this.redisTemplate = redisTemplate;
        this.postVoteScript = postVoteScript;
        this.postUnvoteScript = postUnvoteScript;
        this.postVoteCountScript = postVoteCountScript;
    }

    public static String getPostVotesKey(Long postId) {
        return String.format(POST_VOTES_KEY, postId);
    }

    public PostVoteResponse vote(Long postId, VoteRequest voteRequest) {
        List<String> result = redisTemplate.execute(
            postVoteScript,
            List.of(getPostVotesKey(postId), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            postId.toString(), voteRequest.userId().toString(), voteRequest.voteType().toString()
        );
        return getPostVoteResponse(postId, result);
    }

    public PostVoteResponse unvote(Long postId, Long userId) {
        List<String> result = redisTemplate.execute(
            postUnvoteScript,
            List.of(getPostVotesKey(postId), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            postId.toString(), userId.toString()
        );
        return getPostVoteResponse(postId, result);
    }

    public PostVoteResponse getVoteCount(Long postId) {
        List<String> result = redisTemplate.execute(
            postVoteCountScript,
            List.of(UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            postId.toString()
        );
        return getPostVoteResponse(postId, result);
    }

    private PostVoteResponse getPostVoteResponse(Long postId, List<String> result) {
        if (result == null) {
            throw new IllegalStateException("Redis script returned null");
        }
        Integer upvoteCount = parseInt(result.get(0) == null ? "0" : result.get(0));
        Integer downvoteCount = parseInt(result.get(1) == null ? "0" : result.get(1));
        return new PostVoteResponse(postId, upvoteCount, downvoteCount);
    }

}
