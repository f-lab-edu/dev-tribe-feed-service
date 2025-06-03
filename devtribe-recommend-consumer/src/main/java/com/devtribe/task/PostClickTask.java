package com.devtribe.task;

import com.devtribe.domain.post.dao.PostClickLogRepository;
import com.devtribe.domain.post.entity.PostClickLogDocument;
import com.devtribe.event.PostClickEvent;
import org.springframework.stereotype.Component;

@Component
public class PostClickTask {

    private final PostClickLogRepository postClickLogRepository;

    public PostClickTask(PostClickLogRepository postClickLogRepository) {
        this.postClickLogRepository = postClickLogRepository;
    }

    public void processEvent(PostClickEvent event) {
        PostClickLogDocument postClickLogDocument = new PostClickLogDocument(
            event.postId(),
            event.userId(),
            event.careerLevel(),
            event.careerInterest()
        );
        postClickLogRepository.save(postClickLogDocument);
    }
}
