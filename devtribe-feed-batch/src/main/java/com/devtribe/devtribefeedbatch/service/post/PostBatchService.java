package com.devtribe.devtribefeedbatch.service.post;

import com.devtribe.domain.post.entity.Post;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostBatchService {

    private final PostBatchRepository postBatchRepository;

    public PostBatchService(PostBatchRepository postBatchRepository) {
        this.postBatchRepository = postBatchRepository;
    }

    public void saveAll(List<Post> postList) {
        postBatchRepository.saveAll(postList);
    }

    public int countPost() {
        return postBatchRepository.findAll().size();
    }
}
