package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.post.application.dtos.PostVoteResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<List> postVoteScript;
    private final RedisScript<List> postUnvoteScript;

    public VoteService(
        RedisTemplate<String, String> redisTemplate,
        RedisScript<List> postVoteScript,
        RedisScript<List> postUnvoteScript
    ) {
        this.redisTemplate = redisTemplate;
        this.postVoteScript = postVoteScript;
        this.postUnvoteScript = postUnvoteScript;
    }

    public PostVoteResponse vote(Long postId, VoteRequest voteRequest) {
        String key = "post:" + postId + ":votes";
        String upvoteCountKey = "post:upvoteCount";
        String downvoteCountKey = "post:downvoteCount";

        List<String> result = redisTemplate.execute(
            postVoteScript,
            List.of(key, upvoteCountKey, downvoteCountKey),
            postId.toString(), voteRequest.userId().toString(), voteRequest.voteType().toString()
        );
        return getPostVoteResponse(postId, result);
    }

    public PostVoteResponse unvote(Long postId, Long userId) {
        String setKey = "post:" + postId + ":votes";
        String upvoteCountKey = "post:upvoteCount";
        String downvoteCountKey = "post:downvoteCount";

        List<String> result = redisTemplate.execute(
            postUnvoteScript,
            List.of(setKey, upvoteCountKey, downvoteCountKey),
            postId.toString(), userId.toString()
        );
        return getPostVoteResponse(postId, result);
    }

    private PostVoteResponse getPostVoteResponse(Long postId, List<String> result) {
        /// TODO null - check 개선 필요
        Integer upvoteCount = Integer.parseInt(result.get(0) == null ? "0" : result.get(0));
        Integer downvoteCount = Integer.parseInt(result.get(1) == null ? "0" : result.get(1));
        return new PostVoteResponse(postId, upvoteCount, downvoteCount);
    }

}
