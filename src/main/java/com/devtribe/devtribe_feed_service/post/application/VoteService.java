package com.devtribe.devtribe_feed_service.post.application;

import com.devtribe.devtribe_feed_service.post.application.dtos.PostVoteResponse;
import com.devtribe.devtribe_feed_service.post.application.dtos.VoteRequest;
import com.devtribe.devtribe_feed_service.post.domain.vote.VoteType;
import java.util.List;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final RedisTemplate<String, String> redisTemplate;

    private final String script = """
        local setKey = KEYS[1]
        local removeCountKey = KEYS[2]
        local addCountKey = KEYS[3]
        
        local removeValue = ARGV[1]
        local addValue = ARGV[2]
        local id = ARGV[3]
        
        -- 비추천 제거 시 실제로 제거되었으면 카운트 감소
        if redis.call('SREM', setKey, removeValue) == 1 then
            redis.call('ZINCRBY', removeCountKey, -1, id)
        end
        
        -- 추천 추가
        if redis.call('SADD', setKey, addValue) == 1 then
            redis.call('ZINCRBY', addCountKey, 1, id)
        end
        """;

    private final String cancelScript = """
        local setKey = KEYS[1]
        local countKey = KEYS[2]
        
        local removeValue = ARGV[1]
        local id = ARGV[2]
        
        -- 비추천 제거 시 실제로 제거되었으면 카운트 감소
        if redis.call('SREM', setKey, removeValue) == 1 then
            redis.call('ZINCRBY', countKey, -1, id)
        end
        """;

    public VoteService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public PostVoteResponse vote(Long postId, VoteRequest voteRequest) {
        String key = "post:" + postId + ":votes";
        String upvoteCountKey = "post:upvoteCount";
        String downvoteCountKey = "post:downvoteCount";
        String upvote = voteRequest.userId() + ":" + VoteType.UPVOTE;
        String downvote = voteRequest.userId() + ":" + VoteType.DOWNVOTE;

        if (voteRequest.voteType() == VoteType.UPVOTE) {
            redisTemplate.execute(
                new DefaultRedisScript<>(script, String.class),
                List.of(key, downvoteCountKey, upvoteCountKey),
                downvote, upvote, postId.toString()
            );
        } else {
            redisTemplate.execute(
                new DefaultRedisScript<>(script, String.class),
                List.of(key, upvoteCountKey, downvoteCountKey),
                upvote, downvote, postId.toString()
            );
        }

        return new PostVoteResponse();
    }

    public PostVoteResponse unvote(Long postId, Long userId) {
        String key = "post:" + postId + ":votes";
        String upvoteCountKey = "post:upvoteCount";
        String downvoteCountKey = "post:downvoteCount";
        String upvote = voteRequest.userId() + ":" + VoteType.UPVOTE;
        String downvote = voteRequest.userId() + ":" + VoteType.DOWNVOTE;

        if (voteRequest.voteType() == VoteType.UPVOTE) {
            redisTemplate.execute(
                new DefaultRedisScript<>(cancelScript, String.class),
                List.of(key, upvoteCountKey),
                upvote, postId.toString()
            );
        } else {
            redisTemplate.execute(
                new DefaultRedisScript<>(cancelScript, String.class),
                List.of(key, downvoteCountKey),
                downvote, postId.toString()
            );
        }

        return null;
    }
}
