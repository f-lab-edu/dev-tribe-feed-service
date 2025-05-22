package com.devtribe.domain.vote.application;

import static java.lang.Integer.parseInt;

import com.devtribe.domain.post.application.PostService;
import com.devtribe.domain.post.entity.Post;
import com.devtribe.domain.vote.application.dtos.PostVoteResponse;
import com.devtribe.domain.vote.application.dtos.VoteRequest;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class PostVoteService {

    public static final String POST_VOTES_KEY = "post:%d:votes";
    public static final String UPVOTE_COUNT_KEY = "post:upvoteCount";
    public static final String DOWNVOTE_COUNT_KEY = "post:downvoteCount";

    private final RedisTemplate<String, String> redisTemplate;
    private final PostService postService;

    private final RedisScript<List> postVoteScript;
    private final RedisScript<List> postUnvoteScript;
    private final RedisScript<List> postVoteCountScript;

    public PostVoteService(
        RedisTemplate<String, String> redisTemplate,
        PostService postService,
        RedisScript<List> postVoteScript,
        RedisScript<List> postUnvoteScript,
        RedisScript<List> postVoteCountScript
    ) {
        this.redisTemplate = redisTemplate;
        this.postService = postService;
        this.postVoteScript = postVoteScript;
        this.postUnvoteScript = postUnvoteScript;
        this.postVoteCountScript = postVoteCountScript;
    }

    public static String getPostVotesKey(Long postId) {
        return String.format(POST_VOTES_KEY, postId);
    }

    public PostVoteResponse vote(Long postId, Long userId, VoteRequest voteRequest) {
        Post post = postService.getPost(postId);

        List<String> result = redisTemplate.execute(
            postVoteScript,
            List.of(getPostVotesKey(post.getId()), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            post.getId().toString(), userId.toString(), voteRequest.voteType().toString()
        );
        return getPostVoteResponse(postId, result);
    }

    public PostVoteResponse unvote(Long postId, Long userId) {
        Post post = postService.getPost(postId);

        List<String> result = redisTemplate.execute(
            postUnvoteScript,
            List.of(getPostVotesKey(post.getId()), UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            post.getId().toString(), userId.toString()
        );
        return getPostVoteResponse(postId, result);
    }

    public PostVoteResponse getVoteCount(Long postId) {
        Post post = postService.getPost(postId);

        List<String> result = redisTemplate.execute(
            postVoteCountScript,
            List.of(UPVOTE_COUNT_KEY, DOWNVOTE_COUNT_KEY),
            post.getId().toString()
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
