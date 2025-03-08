package com.devtribe.devtribe_feed_service.post.repository;

import com.devtribe.devtribe_feed_service.post.application.interfaces.PostRepository;
import com.devtribe.devtribe_feed_service.post.domain.Post;
import com.devtribe.devtribe_feed_service.post.repository.jpa.JpaPostRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JpaPostRepository postRepository;

    public PostRepositoryImpl(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postRepository.findById(postId);
    }
}
