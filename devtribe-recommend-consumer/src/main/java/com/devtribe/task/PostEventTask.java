package com.devtribe.task;

import com.devtribe.domain.post.dao.PostRepository;
import com.devtribe.domain.post.entity.PostDocument;
import com.devtribe.event.PostEvent;
import com.devtribe.event.PostEventType;
import org.springframework.stereotype.Component;

@Component
public class PostEventTask {

    private final PostRepository postRepository;

    public PostEventTask(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void process(PostEvent event) {
        PostEventType eventType = event.eventType();

        if (eventType == PostEventType.CREATED) {
            postRepository.save(event.toPostDocument());
        }

        if (eventType == PostEventType.UPDATED) {
            PostDocument postDocument = postRepository.findById(event.postId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
            postDocument.updateTags(event.tags(), event.updatedAt());
            postRepository.save(postDocument);
        }

        if (eventType == PostEventType.DELETED) {
            postRepository.delete(event.toPostDocument());
        }
    }
}
