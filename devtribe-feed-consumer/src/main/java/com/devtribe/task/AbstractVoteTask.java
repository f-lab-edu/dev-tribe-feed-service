package com.devtribe.task;

import com.devtribe.domain.vote.entity.VoteType;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

public abstract class AbstractVoteTask {

    public static final String POST_VOTES_KEY = "post:%d:votes";
    public static final String UPVOTE_COUNT_KEY = "post:upvoteCount";
    public static final String DOWNVOTE_COUNT_KEY = "post:downvoteCount";

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<List> postVoteScript;
    private final RedisScript<List> postUnvoteScript;

    public AbstractVoteTask(
        RedisScript<List> postUnvoteScript,
        RedisTemplate<String, String> redisTemplate,
        RedisScript<List> postVoteScript
    ) {
        this.postUnvoteScript = postUnvoteScript;
        this.redisTemplate = redisTemplate;
        this.postVoteScript = postVoteScript;
    }

    private String getPostVotesKey(Long postId) {
        return String.format(POST_VOTES_KEY, postId);
    }

    public void upvote(Long postId, Long userId, VoteType voteType) {
        redisTemplate.execute(
            postVoteScript,
            List.of(getPostVotesKey(postId), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            postId.toString(),
            userId.toString(),
            voteType.toString()
        );
    }

    public void unvote(Long postId, Long userId) {
        redisTemplate.execute(
            postUnvoteScript,
            List.of(getPostVotesKey(postId), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            postId.toString(), userId.toString()
        );
    }

}
