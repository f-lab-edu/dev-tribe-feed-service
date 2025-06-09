package com.devtribe.step;

import com.devtribe.domain.post.entity.PostClickLogDocument;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClickLogWriter implements ItemWriter<PostClickLogDocument> {

    private final RedisTemplate<String, Object> redisTemplate;

    public ClickLogWriter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Chunk<? extends PostClickLogDocument> items) {
        for (PostClickLogDocument item : items) {
            String careerInterest = item.getCareerInterest();
            String careerLevel = item.getCareerLevel();
            String key =
                "careerInterest:" + careerInterest + ":careerLevel:" + careerLevel + ":ranking";

            redisTemplate.opsForZSet().incrementScore(key, item.getPostId(), 1L);
        }
    }
}
